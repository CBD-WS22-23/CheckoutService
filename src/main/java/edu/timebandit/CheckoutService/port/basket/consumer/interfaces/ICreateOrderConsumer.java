package edu.timebandit.CheckoutService.port.basket.consumer.interfaces;

import edu.timebandit.CheckoutService.port.basket.dtos.OrderDTO;
import jakarta.validation.Valid;

public interface ICreateOrderConsumer {
    void receiveCreateOrderMessage(@Valid OrderDTO orderDTO);
}
