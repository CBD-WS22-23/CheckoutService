package edu.timebandit.CheckoutService.port.payment.consumer;


import edu.timebandit.CheckoutService.core.domain.model.Order;
import edu.timebandit.CheckoutService.core.domain.service.interfaces.ICheckoutService;
import edu.timebandit.CheckoutService.port.payment.dtos.PaymentRequestDTO;
import edu.timebandit.CheckoutService.port.payment.producer.PaymentRequestProducer;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitializePaymentConsumer {
    private final static Logger logger = LoggerFactory.getLogger(InitializePaymentConsumer.class);

    @Autowired
    private ICheckoutService checkoutService;

    @Autowired
    private PaymentRequestProducer paymentRequestProducer;

    @RabbitListener(queues = "initialize_payment_queue")
    public void receiveInitializePaymentMessage(@NotBlank String orderID) {
        logger.info("Received message to initialize payment for order: {}", orderID);

        Order order = checkoutService.getOrderByID(orderID);

        if (order == null) {
            logger.error("Order with ID {} not found", orderID);
            return;
        }

        paymentRequestProducer.sendPaymentRequestMessage(new PaymentRequestDTO(orderID, order.getTotalPrice(),
                order.getPaymentMethod()));
    }
}
