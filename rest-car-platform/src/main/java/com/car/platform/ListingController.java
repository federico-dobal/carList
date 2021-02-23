package com.car.platform;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
class ListingController {

    private final ListingRepository repository;
    private final DealerRepository dealerRepository;

    ListingController(ListingRepository repository, DealerRepository dealerRepository) {
        this.repository = repository;
        this.dealerRepository = dealerRepository;
    }


    /**
     * Get all the listing details
     * @return details of each listing
     */
    @GetMapping("/listings")
    List<Listing> allListings() {
        return repository.findAll();
    }

    /**
     * Get all the listing details
     * @return details of each listing
     */
    @GetMapping("/listings/search")
    List<Listing> searchListings(@RequestParam("make") Optional<String> make,
                                 @RequestParam("model") Optional<String> model,
                                 @RequestParam("year") Optional<Integer> year,
                                 @RequestParam("color") Optional<String> color) {
        Listing listing = new Listing();

        if(make.isPresent()) {
            listing.setMake(make.get());
        }

        if(model.isPresent()) {
            listing.setModel(model.get());
        }

        if(year.isPresent()) {
            listing.setYear(year.get());
        }

        if(color.isPresent()) {
            listing.setColor(color.get());
        }

        return repository.findAll(Example.of(listing));
    }


    /**
     * Upload a list of listings by file upload
     * @param file file with listing details
     * @param dealerId dealer id
     * @return POST response
     */
    @PostMapping("/listings/upload_csv/{dealerId}")
    ResponseEntity<String> uploadCsvListing(@RequestParam("file") MultipartFile file, @PathVariable Long dealerId) {

        // validate file
        Optional<Dealer> dealerFromDb = dealerRepository.findById(dealerId);
        if (!dealerFromDb.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Dealer do not exists");
        }

        if (file.isEmpty()) {
            // TODO: Logger
            System.out.println("File empty");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("File is empty");
        } else {
            List<Listing> allListings = FileParser.parseFile(file, dealerId);
            for (Listing newListing : allListings) {
                Listing listingFromDb = repository.findByCodeAndDealerId(newListing.getCode(), newListing.getDealerId());
                if (listingFromDb == null) {
                    repository.save(newListing);
                } else {
                    newListing.setId(listingFromDb.getId());
                    repository.save(newListing);
                }

            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("All listings imported successfully");
        }
    }

    /**
     * Upload a list of listings by JSON
     * @param newListings list of listings
     * @param dealerId dealer id
     * @return POST response
     */
    @PostMapping("/listings/vehicle_listings/{dealerId}")
    ResponseEntity<String> uploadVehicleListings(@RequestBody List<ListingInput> newListings, @PathVariable Long dealerId) {

        // validate file
        Optional<Dealer> dealerFromDb = dealerRepository.findById(dealerId);
        if (!dealerFromDb.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Dealer do not exists");
        }

        ;
        if (!newListings.stream().allMatch(x-> x.isWellFormed())) {
            // Logger
            System.out.println("Some of the listings are malformed");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Some of the listings are malformed");
        } else {
            for (ListingInput listing : newListings)
            {
                Listing newListing = new Listing(dealerId, listing.getCode(), listing.getMake(), listing.getModel(),
                        listing.getkW(), listing.getYear(), listing.getColor(), listing.getPrice());

                Listing listingFromDb = repository.findByCodeAndDealerId(newListing.getCode(), newListing.getDealerId());
                if (listingFromDb == null) {
                    repository.save(newListing);
                } else {
                    newListing.setId(listingFromDb.getId());
                    repository.save(newListing);
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body("All listings imported successfully");
        }
    }

    /**
     * Retrieves a single listing by its id
     * @param id listing id
     * @return listing detail
     */
    @GetMapping("/listing/{id}")
    Listing one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ListingNotFoundException(id));
    }

}
