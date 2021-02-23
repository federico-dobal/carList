package com.car.platform;

import com.car.platform.controllers.ListingController;
import com.car.platform.entity.Dealer;
import com.car.platform.entity.Listing;
import com.car.platform.model.ListingInput;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ListingControllerUploadVehicleListingsTests {

    @Autowired
    private ListingController listingController;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private DealerRepository dealerRepository;

    private static Long dealerId;

    @Before
    public void setUp() {
        listingRepository.deleteAll();
        dealerRepository.deleteAll();
        dealerRepository.save(new Dealer("dealerName"));
        dealerId = dealerRepository.findByName("dealerName").getId();
    }

    @Test
    public void uploadVehicleListingsIsSuccessfullyListNotEmpty() {

        // GIVEN: the listings are empty
        List<Listing> listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);

        // WHEN: 1 listing is inserted
        List<ListingInput> listingsUpload = Arrays.asList(
                new ListingInput("1", "mercedes", "a 180", 123, 2014, "black", 15950L));
        ResponseEntity<String>  response = listingController.uploadVehicleListings(listingsUpload, dealerId);

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
    public void uploadVehicleListingsIsSuccessfullyListIsEmpty() {

        // GIVEN: the listings are empty
        List<Listing> listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);

        // WHEN: 1 listing is inserted
        List<ListingInput> listingsUpload = new ArrayList<>();
        ResponseEntity<String>  response = listingController.uploadVehicleListings(listingsUpload, dealerId);

        // THEN: listing has been inserted successfully
        String responseMessage = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseMessage).isEqualTo("All listings imported successfully");

        // AND: the listing is retrieved successfully
        listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);
    }

    @Test
    public void uploadVehicleListingsReturnsBadRequestWhenMalformedListingsAreUploaded() {

        // GIVEN: the listings are empty
        List<Listing> listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);

        // WHEN: 1 listing with malformed listings are provided
        List<ListingInput> listingsUpload = Arrays.asList(
                new ListingInput("1", null, "a 180", 123, 2014, "black", 15950L));
        ResponseEntity<String> response = listingController.uploadVehicleListings(listingsUpload, dealerId);

        // THEN: returns bad request
        String responseMessage = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseMessage).isEqualTo("Some of the listings are malformed");
    }


    @Test
    public void uploadVehicleListingsIsSuccessfullyWhenImportingTwiceSameListing() {

        // GIVEN: the listings are empty
        List<Listing> listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);

        // WHEN: 1 listing is imported twice
        List<ListingInput> listingsUpload = Arrays.asList(
                new ListingInput("1", "mercedes", "a 180", 123, 2014, "black", 15950L));
        listingController.uploadVehicleListings(listingsUpload, dealerId);
        ResponseEntity<String>  response = listingController.uploadVehicleListings(listingsUpload, dealerId);

        // THEN: listing has been inserted successfully
        String responseMessage = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseMessage).isEqualTo("All listings imported successfully");

        // AND: the number of listings retrieved is only 1
        listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(1);
    }

    @Test
    public void uploadVehicleListingsIsSuccessfullyWhenImportingLongListing() {

        // GIVEN: the listings are empty
        List<Listing> listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);

        // WHEN: 1 listing is imported many listings
        List<ListingInput> listingsUpload = Arrays.asList(
                new ListingInput("1", "mercedes", "a 180", 123, 2014, "black", 15000L),
                new ListingInput("2", "audi", "a 180", 123, 2012, "red", 15000L),
                new ListingInput("3", "vw", "a 180", 123, 2016, "blue", 15950L),
                new ListingInput("4", "mercedes", "a 180", 123, 2018, "black", 15000L),
                new ListingInput("5", "skoda", "a 180", 123, 2020, "blue", 15950L),
                new ListingInput("6", "mercedes", "a 180", 123, 2018, "red", 15950L),
                new ListingInput("7", "mercedes", "a 180", 123, 2015, "black", 15950L),
                new ListingInput("8", "skoda", "a 180", 123, 2016, "red", 15000L),
                new ListingInput("9", "mercedes", "a 180", 123, 2014, "blue", 15950L),
                new ListingInput("10", "mercedes", "a 180", 123, 2012, "black", 15950L),
                new ListingInput("11", "skoda", "a 180", 123, 2014, "blue", 15000L),
                new ListingInput("12", "mercedes", "a 180", 123, 2016, "black", 15950L),
                new ListingInput("13", "skoda", "a 180", 123, 2018, "red", 15000L),
                new ListingInput("14", "mercedes", "a 180", 123, 2014, "blue", 15000L));
        listingController.uploadVehicleListings(listingsUpload, dealerId);
        ResponseEntity<String>  response = listingController.uploadVehicleListings(listingsUpload, dealerId);

        // THEN: listing has been inserted successfully
        String responseMessage = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseMessage).isEqualTo("All listings imported successfully");

        // AND: the number of listings retrieved is only 14
        listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(14);
    }

    @Test
    public void uploadVehicleListingsNotFoundWhenDealerDoesNotExists() {

        // GIVEN: the listings are empty
        List<Listing> listings = listingController.allListings();
        assertThat(listings.size()).isEqualTo(0);

        // WHEN: 1 unknown dealer is provided
        ResponseEntity<String> response = listingController.uploadVehicleListings(Collections.emptyList(), 12L);

        // THEN: not found response is obtained
        String responseMessage = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseMessage).isEqualTo("Dealer do not exists");
    }
}
