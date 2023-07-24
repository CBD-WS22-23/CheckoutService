package edu.timebandit.CheckoutService.port.payment.consumer;

import edu.timebandit.CheckoutService.core.domain.model.OrderState;
import edu.timebandit.CheckoutService.core.domain.service.interfaces.ICheckoutService;
import edu.timebandit.CheckoutService.port.payment.dtos.PaymentResultDTO;
import edu.timebandit.CheckoutService.port.payment.producer.OrderPaidProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentResultConsumer {
    private final static Logger logger = LoggerFactory.getLogger(PaymentResultConsumer.class);

    @Autowired
    private ICheckoutService checkoutService;

    @Autowired
    OrderPaidProducer orderPaidProducer;

    @RabbitListener(queues = "payment_result_queue")
    public void receivePaymentResultMessage(PaymentResultDTO paymentResult) {
        logger.info("Received message with payment result: {}", paymentResult);

        String orderID = checkoutService.updatePaymentID(paymentResult.getOrderID(), paymentResult.getPaymentID());

        if (orderID == null) {
            logger.error("Order with ID {} not found", paymentResult.getOrderID());
            return;
        }

        if (paymentResult.isPaymentSuccessful()) {
            checkoutService.updateOrderStatus(orderID, OrderState.PAID);
            orderPaidProducer.sendOrderPaidMessage(orderID);
        } else {
            checkoutService.updateOrderStatus(orderID, OrderState.REJECTED);
        }

    }
}
