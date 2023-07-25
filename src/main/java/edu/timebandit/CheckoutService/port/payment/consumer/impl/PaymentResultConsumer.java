package edu.timebandit.CheckoutService.port.payment.consumer.impl;

import edu.timebandit.CheckoutService.core.appservice.interfaces.IProcessPaymentResultAndNotifyServices;
import edu.timebandit.CheckoutService.port.payment.consumer.interfaces.IPaymentResultConsumer;
import edu.timebandit.CheckoutService.port.payment.dtos.PaymentResultDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentResultConsumer implements IPaymentResultConsumer {
    private final static Logger logger = LoggerFactory.getLogger(PaymentResultConsumer.class);

    @Autowired
    private IProcessPaymentResultAndNotifyServices processPaymentResultAndNotifyServices;

    @RabbitListener(queues = "payment_result_queue")
    public void receivePaymentResultMessage(@Valid PaymentResultDTO paymentResult) {
        logger.info("Received message with payment result: {}", paymentResult);

        processPaymentResultAndNotifyServices.processResultAndNotify(paymentResult);
    }
}
