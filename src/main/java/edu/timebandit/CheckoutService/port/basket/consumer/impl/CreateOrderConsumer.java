package edu.timebandit.CheckoutService.port.basket.consumer.impl;

import edu.timebandit.CheckoutService.core.appservice.interfaces.ICreateOrderAndRequestPayment;
import edu.timebandit.CheckoutService.port.basket.consumer.interfaces.ICreateOrderConsumer;
import edu.timebandit.CheckoutService.port.basket.dtos.OrderDTO;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Validated
public class CreateOrderConsumer implements ICreateOrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrderConsumer.class);

    @Autowired
    private ICreateOrderAndRequestPayment createOrderAndRequestPayment;


    @RabbitListener(queues = "checkout_queue")
    @SneakyThrows
    public void receiveCreateOrderMessage(@Valid OrderDTO orderDTO) {
        LOGGER.info("Received message to create order: {}", orderDTO);

        createOrderAndRequestPayment.createAndRequest(orderDTO);
    }

}
