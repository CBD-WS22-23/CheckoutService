package edu.timebandit.CheckoutService.core.domain.service.interfaces;

import edu.timebandit.CheckoutService.core.domain.model.Address;
import edu.timebandit.CheckoutService.core.domain.model.Order;
import edu.timebandit.CheckoutService.core.domain.model.OrderState;
import edu.timebandit.CheckoutService.core.domain.model.Watch;

import java.util.List;

public interface ICheckoutService {

    String createOrder(String basketID, List<Watch> products, Double totalPrice, Address shippingAddress,
                       Address billingAddress,
                       String paymentMethod);

    Order getOrderByID(String orderID);

    Iterable<Order> getAllOrders();

    void deleteOrder(String orderID);

    boolean checkOrderExists(String orderID);

    void updateOrderStatus(String orderID, OrderState status);

    String updatePaymentID(String orderID, String paymentID);
}
