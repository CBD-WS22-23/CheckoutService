package edu.timebandit.CheckoutService.port.payment.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderPaidProducer {

    @Value("checkout_exchange")
    private String exchange;

    @Value("checkout_success_routing_key")
    private String checkoutSuccessRoutingKey;

    private final static Logger logger = LoggerFactory.getLogger(OrderPaidProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public OrderPaidProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderPaidMessage(String orderId) {
        logger.info("Sending message to notify that the order has been paid for order: {}", orderId);
        rabbitTemplate.convertAndSend(exchange, checkoutSuccessRoutingKey, orderId);
    }
}
