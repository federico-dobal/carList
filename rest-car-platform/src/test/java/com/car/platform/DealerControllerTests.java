package com.car.platform;

import com.car.platform.controllers.DealerController;
import com.car.platform.entity.Dealer;
import com.car.platform.model.DealerInput;
import com.car.platform.repository.DealerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DealerControllerTests {

    @Autowired
    private DealerController dealerController;

    @Autowired
    private DealerRepository dealerRepository;

    private static String DEALER_NAME_1 = "dealerName_1";;
    private static String DEALER_NAME_2 = "dealerName_2";;

    @Before
    public void setUp() {
        dealerRepository.deleteAll();
    }

    @Test
    public void addDealer() {

        // GIVEN: no dealers in the DB
        List<Dealer> dealers = dealerController.allDealers();
        assertThat(dealers.size()).isEqualTo(0);

        // GIVEN: A new dealer is added
        ResponseEntity<String>response = dealerController.newDealer(new DealerInput(DEALER_NAME_1));

        // THEN: there is only one dealer and has same name than inserted
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Dealer inserted successfully");

        dealers = dealerController.allDealers();
        assertThat(dealers.size()).isEqualTo(1);

        Dealer actualDealer = dealers.get(0);
        assertThat(actualDealer.getName()).isEqualTo(DEALER_NAME_1);
    }

    @Test
    public void addSameDealerTwice() {

        // GIVEN: no dealers in the DB
        List<Dealer> dealers = dealerController.allDealers();
        assertThat(dealers.size()).isEqualTo(0);

        // WHEN: A new dealer is added
        ResponseEntity<String>response = dealerController.newDealer(new DealerInput(DEALER_NAME_1));

        // AND: the same dealer is tried again
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Dealer inserted successfully");
        response = dealerController.newDealer(new DealerInput(DEALER_NAME_1));

        // THEN: the request fails with BAD_REQUEST
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Dealer cannot be inserted");

        // AND: there is only one dealer and has same name than previous inserted
        dealers = dealerController.allDealers();
        assertThat(dealers.size()).isEqualTo(1);

        Dealer actualDealer = dealers.get(0);
        assertThat(actualDealer.getName()).isEqualTo(DEALER_NAME_1);
    }

    @Test
    public void addTwoDifferentDealers() {

        // GIVEN: no dealers in the DB
        List<Dealer> dealers = dealerController.allDealers();
        assertThat(dealers.size()).isEqualTo(0);

        // WHEN: A new dealer is added
        ResponseEntity<String>response = dealerController.newDealer(new DealerInput(DEALER_NAME_1));

        // AND: the another dealer is tried again
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Dealer inserted successfully");
        response = dealerController.newDealer(new DealerInput(DEALER_NAME_2));

        // THEN: the request fails with BAD_REQUEST
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Dealer inserted successfully");

        // AND: there is only one dealer and has same name than previous inserted
        dealers = dealerController.allDealers();
        assertThat(dealers.size()).isEqualTo(2);

        Dealer actualDealer = dealers.get(0);
        assertThat(actualDealer.getName()).isEqualTo(DEALER_NAME_1);

        actualDealer = dealers.get(1);
        assertThat(actualDealer.getName()).isEqualTo(DEALER_NAME_2);
    }



    @Test
    public void addDealerAndGetDetailsWithSearchById() {

        // GIVEN: no dealers in the DB
        List<Dealer> dealers = dealerController.allDealers();
        assertThat(dealers.size()).isEqualTo(0);

        // GIVEN: A new dealer is added
        ResponseEntity<String>response = dealerController.newDealer(new DealerInput(DEALER_NAME_1));

        // THEN: there is only one dealer and has same name than inserted
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Dealer inserted successfully");

        // AND only one dealer is retrieved
        dealers = dealerController.allDealers();
        assertThat(dealers.size()).isEqualTo(1);

        // AND it can be retrieved by its id
        Dealer actualDealer = dealerController.getSingleDealer(dealers.get(0).getId());
        assertThat(actualDealer.getName()).isEqualTo(DEALER_NAME_1);
    }

    @Test
    public void addSameDealerTwiceAndGetDetailsWithSearchById() {

        // GIVEN: no dealers in the DB
        List<Dealer> dealers = dealerController.allDealers();
        assertThat(dealers.size()).isEqualTo(0);

        // WHEN: A new dealer is added
        ResponseEntity<String>response = dealerController.newDealer(new DealerInput(DEALER_NAME_1));

        // AND: the same dealer is tried again
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Dealer inserted successfully");
        response = dealerController.newDealer(new DealerInput(DEALER_NAME_1));

        // THEN: the request fails with BAD_REQUEST
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Dealer cannot be inserted");

        // AND: there is only one dealer and has same name than previous inserted
        dealers = dealerController.allDealers();
        assertThat(dealers.size()).isEqualTo(1);

        // AND its details is retrieved by its id
        Dealer actualDealer = dealerController.getSingleDealer(dealers.get(0).getId());
        assertThat(actualDealer.getName()).isEqualTo(DEALER_NAME_1);
    }

    @Test
    public void addTwoDifferentDealersAndGetDetailsWithSearchById() {

        // GIVEN: no dealers in the DB
        List<Dealer> dealers = dealerController.allDealers();
        assertThat(dealers.size()).isEqualTo(0);

        // WHEN: A new dealer is added
        ResponseEntity<String>response = dealerController.newDealer(new DealerInput(DEALER_NAME_1));

        // AND: the another dealer is tried again
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Dealer inserted successfully");
        response = dealerController.newDealer(new DealerInput(DEALER_NAME_2));

        // THEN: the request succeeds
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Dealer inserted successfully");

        dealers = dealerController.allDealers();
        assertThat(dealers.size()).isEqualTo(2);

        // AND: the details are retrieved successfully by its ids
        Dealer actualDealer = dealerController.getSingleDealer(dealers.get(0).getId());
        assertThat(actualDealer.getName()).isEqualTo(DEALER_NAME_1);

        actualDealer = dealerController.getSingleDealer(dealers.get(1).getId());
        assertThat(actualDealer.getName()).isEqualTo(DEALER_NAME_2);
    }

}
