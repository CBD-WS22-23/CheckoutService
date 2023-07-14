package edu.timebandit.CheckoutService.core.domain.service.impl;

import edu.timebandit.CheckoutService.core.domain.model.Address;
import edu.timebandit.CheckoutService.core.domain.model.Order;
import edu.timebandit.CheckoutService.core.domain.model.OrderState;
import edu.timebandit.CheckoutService.core.domain.model.Watch;
import edu.timebandit.CheckoutService.core.domain.service.interfaces.ICheckoutRepository;
import edu.timebandit.CheckoutService.core.domain.service.interfaces.ICheckoutService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CheckoutService implements ICheckoutService {

   private final ICheckoutRepository checkoutRepository;

    public CheckoutService(ICheckoutRepository checkoutRepository) {
         this.checkoutRepository = checkoutRepository;
    }


    @Override
    public String createOrder(String basketID, List<Watch> products, Double totalPrice, Address shippingAddress, Address billingAddress, String paymentMethod) {
        Order order = new Order(UUID.randomUUID(), OrderState.PENDING, UUID.fromString(basketID), paymentMethod,
                null, null, totalPrice, shippingAddress, billingAddress);

        Map<Watch, Double> productsMap = new HashMap<>();
        for (Watch product : products) {
            productsMap.put(product, product.getPrice()*product.getOrderQuantity());
        }

        order.setProducts(productsMap);

        return checkoutRepository.save(order).getId().toString();
    }

    @Override
    public Order getOrderByID(String orderID) {
        return checkoutRepository.findById(UUID.fromString(orderID)).orElse(null);
    }

    @Override
    public Iterable<Order> getAllOrders() {
        return checkoutRepository.findAll();
    }

    @Override
    public void deleteOrder(String orderID) {
        checkoutRepository.deleteById(UUID.fromString(orderID));
    }

    @Override
    public boolean checkOrderExists(String orderID) {
        return checkoutRepository.existsById(UUID.fromString(orderID));
    }

    @Override
    @Transactional
    public void updateOrderStatus(String orderID, OrderState status) {
        Order order = checkoutRepository.findById(UUID.fromString(orderID)).orElse(null);
        if (order != null) {
            order.setStatus(status);
            checkoutRepository.save(order);
        }
    }

    @Override
    @Transactional
    public String updatePaymentID(String orderID, String paymentID) {
        Order order = checkoutRepository.findById(UUID.fromString(orderID)).orElse(null);
        if (order != null) {
            order.setPaymentID(UUID.fromString(paymentID));
            checkoutRepository.save(order);
            return order.getId().toString();
        }
        return null;
    }
}
