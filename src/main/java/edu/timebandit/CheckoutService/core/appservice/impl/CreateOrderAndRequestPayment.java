package edu.timebandit.CheckoutService.core.appservice.impl;

import edu.timebandit.CheckoutService.core.appservice.interfaces.ICreateOrderAndRequestPayment;
import edu.timebandit.CheckoutService.core.domain.model.Address;
import edu.timebandit.CheckoutService.core.domain.model.Order;
import edu.timebandit.CheckoutService.core.domain.model.Watch;
import edu.timebandit.CheckoutService.core.domain.service.interfaces.ICheckoutService;
import edu.timebandit.CheckoutService.port.basket.dtos.OrderDTO;
import edu.timebandit.CheckoutService.port.basket.dtos.WatchDTO;
import edu.timebandit.CheckoutService.port.payment.dtos.PaymentRequestDTO;
import edu.timebandit.CheckoutService.port.payment.producer.interfaces.IPaymentRequestProducer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateOrderAndRequestPayment implements ICreateOrderAndRequestPayment {

    @Qualifier("CheckoutModelMapper")
    @Autowired
    private ModelMapper checkoutModelMapper;

    @Autowired
    private ICheckoutService checkoutService;

    @Autowired
    private IPaymentRequestProducer paymentRequestProducer;

    @Override
    public void createAndRequest(OrderDTO orderDTO) {

        List<Watch> checkoutProducts = new ArrayList<>();

        for (WatchDTO watchDTO : orderDTO.getBasket().getWatches()) {
            checkoutProducts.add(checkoutModelMapper.map(watchDTO, Watch.class));
        }

        String orderID = checkoutService.createOrder(orderDTO.getBasket().getId(), checkoutProducts,
                orderDTO.getBasket().getTotalPrice(),
                checkoutModelMapper.map(orderDTO.getShippingAddress(), Address.class),
                checkoutModelMapper.map(orderDTO.getBillingAddress(), Address.class),
                orderDTO.getPaymentMethod());

        Order order = checkoutService.getOrderByID(orderID);

        paymentRequestProducer.sendPaymentRequestMessage(new PaymentRequestDTO(orderID,
                order.getTotalPrice(), order.getPaymentMethod()));
    }
}
