package edu.timebandit.CheckoutService.port.product.consumer;

import edu.timebandit.CheckoutService.core.domain.model.Order;
import edu.timebandit.CheckoutService.core.domain.model.Watch;
import edu.timebandit.CheckoutService.core.domain.service.interfaces.ICheckoutService;
import edu.timebandit.CheckoutService.port.product.producer.ProductBoughtProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductOrderPaidConsumer {

    private final static Logger logger = LoggerFactory.getLogger(ProductOrderPaidConsumer.class);

    @Autowired
    private ICheckoutService checkoutService;

    @Autowired
    private ProductBoughtProducer productBoughtProducer;

    @RabbitListener(queues = "product_checkout_success_queue")
    public void receiveOrderPaidMessage(String orderID) {
        logger.info("Received message that order is finished and stock needs to be updated with orderID: {}", orderID);

        if (!checkoutService.checkOrderExists(orderID)) {
            logger.error("Order with ID {} not found", orderID);
            return;
        }

        Order order = checkoutService.getOrderByID(orderID);
        Map<String, Integer> products = new HashMap<>();

        for (Watch watch : order.getProducts().keySet()) {
            products.put(watch.getId().toString(), watch.getOrderQuantity());
        }

        productBoughtProducer.sendProductBoughtMessage(products);
    }
}
