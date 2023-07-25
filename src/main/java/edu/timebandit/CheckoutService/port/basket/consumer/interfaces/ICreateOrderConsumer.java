package edu.timebandit.CheckoutService.port.basket.consumer.interfaces;

import edu.timebandit.CheckoutService.port.basket.dtos.OrderDTO;

public interface ICreateOrderConsumer {
    void receiveCreateOrderMessage(OrderDTO orderDTO);
}
