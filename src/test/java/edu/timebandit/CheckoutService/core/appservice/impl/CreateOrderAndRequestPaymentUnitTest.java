package edu.timebandit.CheckoutService.core.appservice.impl;

import edu.timebandit.CheckoutService.core.domain.model.Order;
import edu.timebandit.CheckoutService.core.domain.model.OrderState;
import edu.timebandit.CheckoutService.core.domain.model.Watch;
import edu.timebandit.CheckoutService.core.domain.service.interfaces.ICheckoutService;
import edu.timebandit.CheckoutService.port.basket.dtos.BasketDTO;
import edu.timebandit.CheckoutService.port.basket.dtos.OrderDTO;
import edu.timebandit.CheckoutService.port.basket.dtos.WatchDTO;
import edu.timebandit.CheckoutService.port.payment.producer.interfaces.IPaymentRequestProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateOrderAndRequestPaymentUnitTest {
    @Mock
    private ICheckoutService checkoutService;

    @Mock
    private IPaymentRequestProducer paymentRequestProducer;

    @Captor
    private ArgumentCaptor<ArrayList<Watch>> checkoutProductsCaptor;

    @Mock
    private ModelMapper checkoutModelMapper;

    @InjectMocks
    private CreateOrderAndRequestPayment createOrderAndRequestPayment;

    CreateOrderAndRequestPaymentUnitTest() {
    }

    @Test
    void givenDTOWithProducts_whenCreateAndRequest_thenCreateCorrectProductList() {
        //Given
        Mockito.when(checkoutModelMapper.map(Mockito.any(WatchDTO.class), Mockito.any())).thenReturn(new Watch());
        Mockito.when(checkoutService.createOrder(Mockito.any(), Mockito.any(), Mockito.anyDouble(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("orderID");
        Order testOrder = new Order(UUID.randomUUID(), OrderState.PENDING, null,  "PaymentMethod",
                null, null, 0.0, null,
                null);
        Mockito.when(checkoutService.getOrderByID(Mockito.any())).thenReturn(testOrder);
        Mockito.doNothing().when(paymentRequestProducer).sendPaymentRequestMessage(Mockito.any());

        ArrayList<WatchDTO> watchDTOS = new ArrayList<>();
        watchDTOS.add(new WatchDTO("watchID", "watchName", 1.0, 1));
        BasketDTO basketDTO = new BasketDTO("basketID", watchDTOS, 1);
        OrderDTO orderDTO = new OrderDTO(basketDTO, null, null, "paymentMethod");

        //When
        createOrderAndRequestPayment.createAndRequest(orderDTO);

        //Then
        Mockito.verify(checkoutService).createOrder(Mockito.any(), checkoutProductsCaptor.capture(), Mockito.anyDouble(),
                Mockito.any(), Mockito.any(), Mockito.any());

        assertEquals(1, checkoutProductsCaptor.getValue().size());
    }
}