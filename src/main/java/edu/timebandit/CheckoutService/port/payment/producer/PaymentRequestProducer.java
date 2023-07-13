package edu.timebandit.CheckoutService.port.payment.producer;

import edu.timebandit.CheckoutService.port.payment.dtos.PaymentRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentRequestProducer {

    @Value("payment_exchange")
    private String exchange;

    @Value("request_payment_routing_key")
    private String paymentRequestRoutingKey;

    private final static Logger logger = LoggerFactory.getLogger(PaymentRequestProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public PaymentRequestProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPaymentRequestMessage(PaymentRequestDTO paymentDTO) {
        logger.info("Sending message to request payment for order: {}", paymentDTO);
        rabbitTemplate.convertAndSend(exchange, paymentRequestRoutingKey, paymentDTO);
    }
}
