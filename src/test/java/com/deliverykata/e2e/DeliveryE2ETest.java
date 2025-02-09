package com.deliverykata.e2e;

import com.deliverykata.dto.CustomerDto;
import com.deliverykata.dto.DeliveryDto;
import com.deliverykata.dto.DeliveryModeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeliveryE2ETest {

    @Autowired
    private WebTestClient webTestClient;

    private CustomerDto testCustomer;
    private DeliveryModeDto testDeliveryMode;
    private DeliveryDto testDelivery;

    @BeforeEach
    void setUp() {
        testCustomer = CustomerDto.builder().name("John Doe").email("john.doe@example.com").build();

        testDeliveryMode = DeliveryModeDto.builder().name("DELIVERY").active(true).build();
    }

    @Test
    public void testEndToEndDeliveryProcess() {
        CustomerDto createdCustomer = webTestClient.post()
                                                   .uri("/customer")
                                                   .bodyValue(testCustomer)
                                                   .exchange()
                                                   .expectStatus()
                                                   .isOk()
                                                   .expectBody(CustomerDto.class)
                                                   .returnResult()
                                                   .getResponseBody();

        assertNotNull(createdCustomer);
        assertNotNull(createdCustomer.getId());

        // Create Delivery Mode
        DeliveryModeDto createdDeliveryMode = webTestClient.post()
                                                           .uri("/delivery-modes")
                                                           .bodyValue(testDeliveryMode)
                                                           .exchange()
                                                           .expectStatus()
                                                           .isOk()
                                                           .expectBody(DeliveryModeDto.class)
                                                           .returnResult()
                                                           .getResponseBody();

        assertNotNull(createdDeliveryMode);
        assertNotNull(createdDeliveryMode.getId());

        // Create Delivery
        testDelivery = DeliveryDto.builder()
                                  .customerId(createdCustomer.getId())
                                  .deliveryModeId(createdDeliveryMode.getId())
                                  .deliveryTimeSlot(LocalDateTime.now())
                                  .build();

        DeliveryDto createdDelivery = webTestClient.post()
                                                   .uri("/deliveries")
                                                   .bodyValue(testDelivery)
                                                   .exchange()
                                                   .expectStatus()
                                                   .isOk()
                                                   .expectBody(DeliveryDto.class)
                                                   .returnResult()
                                                   .getResponseBody();

        assertNotNull(createdDelivery);
        assertNotNull(createdDelivery.getId());

        // Verify Delivery is in /all endpoint
        webTestClient.get()
                     .uri("/deliveries/all/customer/" + createdCustomer.getId())
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBodyList(DeliveryDto.class)
                     .consumeWith(response -> {
                         assertFalse(response.getResponseBody().isEmpty());
                         assertTrue(response.getResponseBody().stream().anyMatch(delivery -> delivery.getId().equals(createdDelivery.getId())));
                     });

        // Verify Delivery by ID
        webTestClient.get()
                     .uri("/deliveries/" + createdDelivery.getId() + "/customer/" + createdCustomer.getId())
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(DeliveryDto.class)
                     .consumeWith(response -> {
                         DeliveryDto retrievedDelivery = response.getResponseBody();
                         assertNotNull(retrievedDelivery);
                         assertEquals(createdDelivery.getId(), retrievedDelivery.getId());
                         assertEquals(createdDelivery.getCustomerId(), retrievedDelivery.getCustomerId());
                     });
    }
}
