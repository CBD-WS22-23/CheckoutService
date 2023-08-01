package edu.timebandit.CheckoutService.port.user.controller;

import edu.timebandit.CheckoutService.core.domain.model.Order;
import edu.timebandit.CheckoutService.core.domain.service.impl.CheckoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckoutController.class)
class CheckoutControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckoutService checkoutService;

    @Test
    void givenOrders_whenGetOrders_thenReturnJsonArray() {
        //Given
        Order order = Order.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .basketID(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .paymentMethod("PayPal")
                .totalPrice(100.0)
                .shippingAddress(null)
                .billingAddress(null)
                .products(null)
                .build();
        List<Order> orders = Collections.singletonList(order);
        given(checkoutService.getAllOrders()).willReturn(orders);

        //When&Then
        try {
            mockMvc.perform(get("/cs/api/v1/order"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].id", is(order.getId().toString())))
                    .andExpect(jsonPath("$[0].basketID", is(order.getBasketID().toString())))
                    .andExpect(jsonPath("$[0].paymentMethod", is(order.getPaymentMethod())))
                    .andExpect(jsonPath("$[0].totalPrice", is(order.getTotalPrice())))
                    .andExpect(jsonPath("$[0].shippingAddress", is(order.getShippingAddress())))
                    .andExpect(jsonPath("$[0].billingAddress", is(order.getBillingAddress())))
                    .andExpect(jsonPath("$[0].products", is(order.getProducts())));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void whenGivenOrders_whenGetOrderByID_thenReturnJson() {
        //Given
        Order order = Order.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .basketID(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .paymentMethod("PayPal")
                .totalPrice(100.0)
                .shippingAddress(null)
                .billingAddress(null)
                .products(null)
                .build();
        given(checkoutService.getOrderByID("00000000-0000-0000-0000-000000000000")).willReturn(order);

        //When&Then
        try {
            mockMvc.perform(get("/cs/api/v1/order/00000000-0000-0000-0000-000000000000"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(order.getId().toString())))
                    .andExpect(jsonPath("$.basketID", is(order.getBasketID().toString())))
                    .andExpect(jsonPath("$.paymentMethod", is(order.getPaymentMethod())))
                    .andExpect(jsonPath("$.totalPrice", is(order.getTotalPrice())))
                    .andExpect(jsonPath("$.shippingAddress", is(order.getShippingAddress())))
                    .andExpect(jsonPath("$.billingAddress", is(order.getBillingAddress())))
                    .andExpect(jsonPath("$.products", is(order.getProducts())));

        } catch (Exception e) {
            fail();
        }
    }
}