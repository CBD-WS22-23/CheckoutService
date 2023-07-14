package edu.timebandit.CheckoutService.port.basket.consumer;


import edu.timebandit.CheckoutService.core.domain.service.interfaces.ICheckoutService;
import edu.timebandit.CheckoutService.port.basket.producer.EmptyBasketProducer;
import edu.timebandit.CheckoutService.port.product.consumer.ProductOrderPaidConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketOrderPaidConsumer {

    private final static Logger logger = LoggerFactory.getLogger(ProductOrderPaidConsumer.class);

    @Autowired
    private ICheckoutService checkoutService;

    @Autowired
    private EmptyBasketProducer emptyBasketProducer;

    @RabbitListener(queues = "basket_checkout_success_queue")
    public void receiveOrderPaidMessage(String orderID) {
        logger.info("Received message that order is finished and basket needs to be emptied with orderID: {}", orderID);

        if (!checkoutService.checkOrderExists(orderID)) {
            logger.error("Order with ID {} not found", orderID);
            return;
        }

        emptyBasketProducer.sendEmptyBasketMessage(checkoutService.getOrderByID(orderID).getBasketID().toString());
    }
}
