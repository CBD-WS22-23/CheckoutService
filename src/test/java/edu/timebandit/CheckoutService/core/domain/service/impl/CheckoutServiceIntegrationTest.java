package edu.timebandit.CheckoutService.core.domain.service.impl;


import edu.timebandit.CheckoutService.core.domain.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = {CheckoutService.class})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "edu.timebandit.CheckoutService.core.domain.service.interfaces")
@EntityScan(basePackages = "edu.timebandit.CheckoutService.core.domain.model")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CheckoutServiceIntegrationTest {

    @Autowired
    private CheckoutService checkoutService;

    @Test
    void givenParameters_whenCreateOrder_thenVerifyThatOrderIsSaved() {
        //Given
        //When
        checkoutService.createOrder("00000000-0000-0000-0000-000000000000", new ArrayList<>(), 1.0,
                null, null, "paymentMethod");

        List<Order> orders = new ArrayList<>();
        checkoutService.getAllOrders().forEach(orders::add);
        //Then
        assertEquals(1, orders.size());
        assertEquals("00000000-0000-0000-0000-000000000000", orders.get(0).getBasketID().toString());
        assertEquals(1.0, orders.get(0).getTotalPrice());
        assertEquals("paymentMethod", orders.get(0).getPaymentMethod());
    }

    @Test
    void givenOrderID_whenGetOrder_thenVerifyThatOrderIsReturned() {
        //Given
        String orderID = checkoutService.createOrder("00000000-0000-0000-0000-000000000000", new ArrayList<>(), 1.0,
                null, null, "paymentMethod");
        //When
        Order order = checkoutService.getOrderByID(orderID);
        //Then
        assertEquals("00000000-0000-0000-0000-000000000000", order.getBasketID().toString());
    }

    @Test
    void givenOrderID_whenDeleteOrder_thenVerifyThatOrderIsDeleted() {
        //Given
        String orderID = checkoutService.createOrder("00000000-0000-0000-0000-000000000000", new ArrayList<>(), 1.0,
                null, null, "paymentMethod");
        //When
        checkoutService.deleteOrder(orderID);
        //Then
        assertFalse(checkoutService.checkOrderExists(orderID));
    }

}
