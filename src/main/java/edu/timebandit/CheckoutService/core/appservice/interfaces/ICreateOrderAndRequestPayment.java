package edu.timebandit.CheckoutService.core.appservice.interfaces;

import edu.timebandit.CheckoutService.port.basket.dtos.OrderDTO;

public interface ICreateOrderAndRequestPayment {
    void createAndRequest(OrderDTO orderDTO);
}
