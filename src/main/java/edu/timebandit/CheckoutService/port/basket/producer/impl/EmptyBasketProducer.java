package edu.timebandit.CheckoutService.port.basket.producer.impl;

import edu.timebandit.CheckoutService.port.basket.producer.interfaces.IEmptyBasketProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmptyBasketProducer implements IEmptyBasketProducer {

    @Value("checkout_exchange")
    private String exchange;

    @Value("empty_basket_routing_key")
    private String emptyBasketRoutingKey;

    private final static Logger logger = LoggerFactory.getLogger(EmptyBasketProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public EmptyBasketProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmptyBasketMessage(String basketID) {
        logger.info("Sending message to empty basket: {}", basketID);
        rabbitTemplate.convertAndSend(exchange, emptyBasketRoutingKey, basketID);
    }
}
