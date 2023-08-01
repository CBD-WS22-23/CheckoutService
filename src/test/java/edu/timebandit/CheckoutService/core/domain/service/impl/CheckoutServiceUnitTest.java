package edu.timebandit.CheckoutService.core.domain.service.impl;

import edu.timebandit.CheckoutService.core.domain.model.Order;
import edu.timebandit.CheckoutService.core.domain.model.OrderState;
import edu.timebandit.CheckoutService.core.domain.model.Watch;
import edu.timebandit.CheckoutService.core.domain.service.interfaces.ICheckoutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceUnitTest {

    @Mock
    private ICheckoutRepository checkoutRepository;

    @Captor
    private ArgumentCaptor<Order> orderCaptor;

    @InjectMocks
    private CheckoutService checkoutService;


    @Test
    void givenOrderParameters_whenCreateOrder_thenCreateCorrectProductsMap() {
        //Given
        Order order = Mockito.mock(Order.class);
        Mockito.when(order.getId()).thenReturn(UUID.randomUUID());
        Mockito.when(checkoutRepository.save(Mockito.any())).thenReturn(order);
        List<Watch> products = List.of(new Watch(UUID.randomUUID(), "testWatch1", 20.0, 3),
                new Watch(UUID.randomUUID(), "testWatch2", 250.0, 1));

        //When
        checkoutService.createOrder("6ba94a65-836d-4a9a-a5cc-4090bd3adbee", products,
                310.0, null, null, "paymentMethod");

        Mockito.verify(checkoutRepository).save(orderCaptor.capture());
        Order capturedOrder = orderCaptor.getValue();

        //Then
        assertEquals(2, capturedOrder.getProducts().size());
        assertEquals(60.0, capturedOrder.getProducts().get(products.get(0)));
        assertEquals(250.0, capturedOrder.getProducts().get(products.get(1)));
        assertTrue(capturedOrder.getProducts().containsKey(products.get(0)));
        assertTrue(capturedOrder.getProducts().containsKey(products.get(1)));
    }

    @Test
    void givenOrderParameters_whenCreateOrder_thenSetCorrectBasketID() {
        //Given
        Order order = Mockito.mock(Order.class);
        Mockito.when(order.getId()).thenReturn(UUID.randomUUID());
        Mockito.when(checkoutRepository.save(Mockito.any())).thenReturn(order);
        List<Watch> products = List.of(new Watch(UUID.randomUUID(), "testWatch1", 20.0, 3),
                new Watch(UUID.randomUUID(), "testWatch2", 250.0, 1));

        //When
        checkoutService.createOrder("6ba94a65-836d-4a9a-a5cc-4090bd3adbee", products,
                0.0, null, null, "paymentMethod");

        Mockito.verify(checkoutRepository).save(orderCaptor.capture());
        Order capturedOrder = orderCaptor.getValue();

        //Then
        assertEquals(UUID.fromString("6ba94a65-836d-4a9a-a5cc-4090bd3adbee"), capturedOrder.getBasketID());
    }

    @Test
    void givenNewOrderStatus_whenUpdateOrder_thenSetCorrectStatus() {
        //Given
        Order order = new Order();
        order.setStatus(OrderState.PENDING);
        Mockito.when(checkoutRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(order));

        //When
        checkoutService.updateOrderStatus("00000000-0000-0000-0000-000000000000", OrderState.PAID);

        //Then
        assertEquals(OrderState.PAID, order.getStatus());
    }

    @Test
    void givenNewPaymentID_whenUpdatePaymentID_thenReturnCorrectUUID() {
        //Given
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setPaymentID(UUID.randomUUID());
        Mockito.when(checkoutRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(order));
        String paymentID = UUID.randomUUID().toString();

        //When
        checkoutService.updatePaymentID("00000000-0000-0000-0000-000000000000",
                paymentID);

        //Then
        assertEquals(order.getPaymentID().toString(), paymentID);
    }
}