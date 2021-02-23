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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ListingControllerSearchTests {

    @Autowired
    private ListingController controller;

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

        // WHEN: 1 listing is imported many listings
        List<ListingInput> listingsUpload = Arrays.asList(
                new ListingInput("1", "mercedes", "1", 123, 2014, "black", 15000L),
                new ListingInput("2", "audi", "1", 123, 2012, "red", 15000L),
                new ListingInput("3", "vw", "2", 123, 2016, "blue", 15950L),
                new ListingInput("4", "mercedes", "1", 123, 2018, "black", 15000L),
                new ListingInput("5", "skoda", "3", 123, 2020, "blue", 15950L),
                new ListingInput("6", "mercedes", "4", 123, 2018, "red", 15950L),
                new ListingInput("7", "mercedes", "5", 123, 2015, "black", 15950L),
                new ListingInput("8", "skoda", "6", 123, 2016, "red", 15000L),
                new ListingInput("9", "mercedes", "7", 123, 2014, "blue", 15950L),
                new ListingInput("10", "mercedes", "5", 123, 2012, "black", 15950L),
                new ListingInput("11", "skoda", "a 180", 123, 2014, "blue", 15000L),
                new ListingInput("12", "mercedes", "a 180", 123, 2016, "blue", 15950L),
                new ListingInput("13", "skoda", "a 180", 123, 2018, "red", 15000L),
                new ListingInput("14", "mercedes", "a 180", 123, 2016, "blue", 15000L));
        controller.uploadVehicleListings(listingsUpload, dealerId);

    }

    @Test
    public void searchListingsIsSuccessfullyWhenNoneFilterIsSet() {

        // GIVEN: 14 listing on the DB
        List<Listing> listings = controller.allListings();
        assertThat(listings.size()).isEqualTo(14);

        // GIVEN: No filters are applied
        listings = controller.searchListings(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        // THEN: The number of listing is the same
        assertThat(listings.size()).isEqualTo(14);

    }

    @Test
    public void searchListingsIsSuccessfullyWhenAllFilterAreAppliedNonEmptyResults() {

        // GIVEN: 14 listing on the DB
        List<Listing> listings = controller.allListings();
        assertThat(listings.size()).isEqualTo(14);

        // GIVEN: Make filter is applied
        listings = controller.searchListings(Optional.of("mercedes"), Optional.of("a 180"), Optional.of(2016), Optional.of("blue"));

        // THEN: The number of listing retrieved is right and all its value agree with the filters
        assertThat(listings.size()).isEqualTo(2);
        assertThat(listings.stream().allMatch(l -> "mercedes".equals(l.getMake()))).isTrue();
        assertThat(listings.stream().allMatch(l -> "a 180".equals(l.getModel()))).isTrue();
        assertThat(listings.stream().allMatch(l -> 2016  == l.getYear())).isTrue();
        assertThat(listings.stream().allMatch(l -> "blue".equals(l.getColor()))).isTrue();
    }

    @Test
    public void searchListingsIsSuccessfullyWhenAllFilterAreAppliedEmptyResults() {

        // GIVEN: 14 listing on the DB
        List<Listing> listings = controller.allListings();
        assertThat(listings.size()).isEqualTo(14);

        // GIVEN: Make filter is applied
        listings = controller.searchListings(Optional.of("mercedes"), Optional.of("a 100"), Optional.of(2016), Optional.of("blue"));

        // THEN: The number of listing retrieved is right and all its value agree with the filters
        assertThat(listings.size()).isEqualTo(0);

    }

    @Test
    public void searchListingsIsSuccessfullyWhenOnlyMakeFilterIsApplied() {

        // GIVEN: 14 listing on the DB
        List<Listing> listings = controller.allListings();
        assertThat(listings.size()).isEqualTo(14);

        // GIVEN: Make filter is applied
        listings = controller.searchListings(Optional.of("skoda"), Optional.empty(), Optional.empty(), Optional.empty());

        // THEN: The number of listing retrieved is right and its make is skoda
        assertThat(listings.size()).isEqualTo(4);
        assertThat(listings.stream().allMatch(l -> "skoda".equals(l.getMake()))).isTrue();

    }

    @Test
    public void searchListingsIsSuccessfullyWhenOnlyModelFilterIsApplied() {

        // GIVEN: 14 listing on the DB
        List<Listing> listings = controller.allListings();
        assertThat(listings.size()).isEqualTo(14);

        // GIVEN: Model filter is applied
        listings = controller.searchListings(Optional.empty(), Optional.of("3"), Optional.empty(), Optional.empty());

        // THEN: The number of listing retrieved is right and its model is 3
        assertThat(listings.size()).isEqualTo(1);
        assertThat(listings.stream().allMatch(l -> "3".equals(l.getModel()))).isTrue();
    }

    @Test
    public void searchListingsIsSuccessfullyWhenOnlyYearFilterIsApplied() {

        // GIVEN: 14 listing on the DB
        List<Listing> listings = controller.allListings();
        assertThat(listings.size()).isEqualTo(14);

        // GIVEN: Year filter is applied
        listings = controller.searchListings(Optional.empty(), Optional.empty(), Optional.of(2018), Optional.empty());

        // THEN: The number of listing retrieved is right and its year is 2018
        assertThat(listings.size()).isEqualTo(3);
        assertThat(listings.stream().allMatch(l -> 2018 == l.getYear().longValue())).isTrue();
    }

    @Test
    public void searchListingsIsSuccessfullyWhenOnlyColorFilterIsApplied() {

        // GIVEN: 14 listing on the DB
        List<Listing> listings = controller.allListings();
        assertThat(listings.size()).isEqualTo(14);

        // GIVEN: Color filter is applied
        listings = controller.searchListings(Optional.empty(), Optional.empty(), Optional.empty(), Optional.of("black"));

        // THEN: The number of listing retrieved is right and its color is black
        assertThat(listings.size()).isEqualTo(4);
        assertThat(listings.stream().allMatch(l -> "black".equals(l.getColor()))).isTrue();
    }
}
