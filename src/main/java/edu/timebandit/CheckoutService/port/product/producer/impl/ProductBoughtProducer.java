package edu.timebandit.CheckoutService.port.product.producer.impl;

import edu.timebandit.CheckoutService.port.product.dtos.ProductBoughtDTO;
import edu.timebandit.CheckoutService.port.product.producer.interfaces.IProductBoughtProducer;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Validated
public class ProductBoughtProducer implements IProductBoughtProducer {

    @Value("checkout_exchange")
    private String exchange;

    @Value("product_bought_routing_key")
    private String productBoughtRoutingKey;

    private final static Logger logger = LoggerFactory.getLogger(ProductBoughtProducer.class);

    private final RabbitTemplate rabbitTemplate;
    
    public ProductBoughtProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void sendProductBoughtMessage(@Valid ProductBoughtDTO products) {
        logger.info("Sending message to decrease stock of: {}", products);
        rabbitTemplate.convertAndSend(exchange, productBoughtRoutingKey, products);
    }
    
}
