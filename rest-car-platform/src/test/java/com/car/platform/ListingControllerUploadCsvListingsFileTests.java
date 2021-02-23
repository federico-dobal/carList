package com.car.platform;

import com.car.platform.controllers.ListingController;
import com.car.platform.entity.Dealer;
import com.car.platform.entity.Listing;
import com.car.platform.repository.DealerRepository;
import com.car.platform.repository.ListingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ListingControllerUploadCsvListingsFileTests {

    @Autowired
    private ListingController listingController;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private DealerRepository dealerRepository;

    private static Long dealerId;
    private static MultipartFile multipartFile;

    @Before
    public void setUp() throws IOException {
        listingRepository.deleteAll();
        dealerRepository.deleteAll();
        dealerRepository.save(new Dealer("dealerName"));
        dealerId = dealerRepository.findByName("dealerName").getId();
        File file = new File(FileParserTest.class.getClassLoader().getResource("listing_one.csv").getFile());
        multipartFile = new MockMultipartFile("file", new FileInputStream(file));
    }

    @Test
    public void uploadCsvListingIsSuccessfullyListNotEmpty() {

        // GIVEN: the listings are empty
        List<Listing> listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);

        // WHEN: 1 listing is inserted
        ResponseEntity<String>  response = listingController.uploadCsvListing(multipartFile, dealerId);

        // THEN: listing has been inserted successfully
        String responseMessage = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseMessage).isEqualTo("All listings imported successfully");

        // AND: the listing is retrieved successfully
        listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(1);

        // AND: the listing details are correct
        Listing listingRetrieved = listings.get(0);
        assertThat(listingRetrieved.getCode()).isEqualTo("1");
        assertThat(listingRetrieved.getMake()).isEqualTo("mercedes");;
        assertThat(listingRetrieved.getModel()).isEqualTo("a 180");;
        assertThat(listingRetrieved.getPowerInPs().intValue()).isEqualTo(123);;
        assertThat(listingRetrieved.getYear().intValue()).isEqualTo(2014);;
        assertThat(listingRetrieved.getColor()).isEqualTo("black");;
        assertThat(listingRetrieved.getPrice().longValue()).isEqualTo(15950L);;
    }

    @Test
    public void uploadCsvListingIsSuccessfullyListIsEmpty() throws IOException{

        // GIVEN: the listings are empty
        List<Listing> listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);

        // WHEN: 1 listing is inserted
        File file = new File(FileParserTest.class.getClassLoader().getResource("listing_empty.csv").getFile());
        MultipartFile emptyMultipartFile = new MockMultipartFile("file", new FileInputStream(file));
        ResponseEntity<String>  response = listingController.uploadCsvListing(emptyMultipartFile, dealerId);

        // THEN: listing has been inserted successfully
        String responseMessage = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseMessage).isEqualTo("All listings imported successfully");

        // AND: the listing is retrieved successfully
        listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);
    }

    @Test
    public void uploadCsvListingIsSuccessfullyWhenImportingTwiceSameListing() {

        // GIVEN: the listings are empty
        List<Listing> listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);

        // WHEN: 1 listing is imported twice
        listingController.uploadCsvListing(multipartFile, dealerId);
        ResponseEntity<String> response = listingController.uploadCsvListing(multipartFile, dealerId);

        // THEN: listing has been inserted successfully
        String responseMessage = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseMessage).isEqualTo("All listings imported successfully");

        // AND: the number of listings retrieved is only 1
        listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(1);
    }

    @Test
    public void uploadCsvListingIsSuccessfullyWhenImportingLongListing() throws IOException {

        // GIVEN: the listings are empty
        List<Listing> listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);

        // WHEN: 1 listing is imported many listings
        File file = new File(FileParserTest.class.getClassLoader().getResource("listing_long.csv").getFile());
        MultipartFile longMultipartFile = new MockMultipartFile("file", new FileInputStream(file));
        ResponseEntity<String> response = listingController.uploadCsvListing(longMultipartFile, dealerId);

        // THEN: listing has been inserted successfully
        String responseMessage = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseMessage).isEqualTo("All listings imported successfully");

        // AND: the number of listings retrieved is 15
        listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(15);
    }

    @Test
    public void uploadCsvListingBadRequestWhenImportingEmptyFile() throws IOException {

        // GIVEN: the listings are empty
        List<Listing> listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);

        // WHEN: 1 listing is imported many listings
        File file = new File(ListingControllerUploadCsvListingsFileTests.class.getClassLoader().getResource("empty.csv").getFile());
        MultipartFile longMultipartFile = new MockMultipartFile("file", new FileInputStream(file));
        ResponseEntity<String> response = listingController.uploadCsvListing(longMultipartFile, dealerId);

        // THEN: bad request response is obtained
        String responseMessage = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseMessage).isEqualTo("File is empty");
    }

    @Test
    public void uploadCsvListingNotFoundWhenDealerDoesNotExists() {

        // GIVEN: the listings are empty
        List<Listing> listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);

        // WHEN: 1 unknown dealer is provided
        ResponseEntity<String> response = listingController.uploadCsvListing(multipartFile, 12L);

        // THEN: not found response is obtained
        String responseMessage = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseMessage).isEqualTo("Dealer do not exists");
    }
}
