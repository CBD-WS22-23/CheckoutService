package edu.timebandit.CheckoutService.port.basket.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InitializePaymentProducer {

    @Value("checkout_exchange")
    private String exchange;

    @Value("initialize_payment_routing_key")
    private String initializePaymentRoutingKey;

    private final static Logger logger = LoggerFactory.getLogger(InitializePaymentProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public InitializePaymentProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendInitializePaymentMessage(String orderID) {
        logger.info("Sending message to initialize payment for order: {}", orderID);
        rabbitTemplate.convertAndSend(exchange, initializePaymentRoutingKey, orderID);
    }

}
