package edu.timebandit.CheckoutService.port.basket.consumer;

import edu.timebandit.CheckoutService.core.domain.model.Address;
import edu.timebandit.CheckoutService.core.domain.model.Watch;
import edu.timebandit.CheckoutService.core.domain.service.interfaces.ICheckoutService;
import edu.timebandit.CheckoutService.port.basket.dtos.OrderDTO;
import edu.timebandit.CheckoutService.port.basket.dtos.WatchDTO;
import edu.timebandit.CheckoutService.port.basket.producer.InitializePaymentProducer;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CreateOrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrderConsumer.class);

    @Qualifier("CheckoutModelMapper")
    @Autowired
    private ModelMapper checkoutModelMapper;

    @Autowired
    private ICheckoutService checkoutService;

    @Autowired
    InitializePaymentProducer initializePaymentProducer;


    @RabbitListener(queues = "checkout_queue")
    public void receiveCreateOrderMessage(OrderDTO orderDTO) {
        LOGGER.info("Received message to create order: {}", orderDTO);

        List<Watch> checkoutProducts = new ArrayList<>();

        for (WatchDTO watchDTO : orderDTO.getBasket().getWatches()) {
            checkoutProducts.add(checkoutModelMapper.map(watchDTO, Watch.class));
        }

        String orderID = checkoutService.createOrder(orderDTO.getBasket().getId(), checkoutProducts,
                orderDTO.getBasket().getTotalPrice(),
                checkoutModelMapper.map(orderDTO.getShippingAddress(), Address.class),
                checkoutModelMapper.map(orderDTO.getBillingAddress(), Address.class),
                orderDTO.getPaymentMethod());

        initializePaymentProducer.sendInitializePaymentMessage(orderID);
    }

}
