package edu.timebandit.CheckoutService.port.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("checkout_queue")
    private String checkoutName;

    @Value("checkout_routing_key")
    private String checkoutRoutingKey;

    @Value("product_bought_queue")
    private String productBoughtName;

    @Value("product_bought_routing_key")
    private String productBoughtRoutingKey;

    @Value("empty_basket_queue")
    private String emptyBasketName;

    @Value("empty_basket_routing_key")
    private String emptyBasketRoutingKey;

    @Value("checkout_exchange")
    private String checkoutExchange;

    @Value("request_payment_queue")
    private String paymentName;

    @Value("request_payment_routing_key")
    private String paymentRoutingKey;

    @Value("payment_result_queue")
    private String paymentResultName;

    @Value("payment_result_routing_key")
    private String paymentResultRoutingKey;

    @Value("payment_exchange")
    private String paymentExchange;


    @Bean
    public Queue checkoutQueue() {
        return new Queue(checkoutName);
    }

    @Bean
    public Queue productBoughtQueue() {
        return new Queue(productBoughtName);
    }

    @Bean
    public Queue emptyBasketQueue() {
        return new Queue(emptyBasketName);
    }

    @Bean
    public DirectExchange checkoutExchange() {
        return new DirectExchange(checkoutExchange);
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(paymentName);
    }

    @Bean
    public Queue paymentResultQueue() {
        return new Queue(paymentResultName);
    }

    @Bean
    public DirectExchange paymentExchange() {
        return new DirectExchange(paymentExchange);
    }

    @Bean
    public Binding bindingCheckoutQueue() {
        return BindingBuilder
                .bind(checkoutQueue())
                .to(checkoutExchange())
                .with(checkoutRoutingKey);
    }

    @Bean
    public Binding bindingPaymentQueue() {
        return BindingBuilder
                .bind(paymentQueue())
                .to(paymentExchange())
                .with(paymentRoutingKey);
    }

    @Bean
    public Binding bindingPaymentResultQueue() {
        return BindingBuilder
                .bind(paymentResultQueue())
                .to(paymentExchange())
                .with(paymentResultRoutingKey);
    }

    @Bean
    public Binding bindingProductBoughtQueue() {
        return BindingBuilder
                .bind(productBoughtQueue())
                .to(checkoutExchange())
                .with(productBoughtRoutingKey);
    }

    @Bean
    public Binding bindingEmptyBasketQueue() {
        return BindingBuilder
                .bind(emptyBasketQueue())
                .to(checkoutExchange())
                .with(emptyBasketRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
