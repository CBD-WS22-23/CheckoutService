package edu.timebandit.CheckoutService.core.appservice.impl;

import edu.timebandit.CheckoutService.core.appservice.interfaces.IProcessPaymentResultAndNotifyServices;
import edu.timebandit.CheckoutService.core.domain.model.Order;
import edu.timebandit.CheckoutService.core.domain.model.OrderState;
import edu.timebandit.CheckoutService.core.domain.model.Watch;
import edu.timebandit.CheckoutService.core.domain.service.interfaces.ICheckoutService;
import edu.timebandit.CheckoutService.port.basket.producer.interfaces.IEmptyBasketProducer;
import edu.timebandit.CheckoutService.port.payment.dtos.PaymentResultDTO;
import edu.timebandit.CheckoutService.port.product.dtos.ProductBoughtDTO;
import edu.timebandit.CheckoutService.port.product.producer.interfaces.IProductBoughtProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProcessPaymentResultAndNotifyServices implements IProcessPaymentResultAndNotifyServices {

    @Autowired
    private ICheckoutService checkoutService;

    @Autowired
    private IEmptyBasketProducer emptyBasketProducer;

    @Autowired
    private IProductBoughtProducer productBoughtProducer;

    @Override
    public void processResultAndNotify(PaymentResultDTO paymentResult) {
        String orderID = checkoutService.updatePaymentID(paymentResult.getOrderID(), paymentResult.getPaymentID());
        if (orderID != null) {
            Order order = checkoutService.getOrderByID(orderID);

            if (paymentResult.isPaymentSuccessful()) {
                checkoutService.updateOrderStatus(orderID, OrderState.PAID);
                emptyBasketProducer.sendEmptyBasketMessage(order.getBasketID().toString());
                productBoughtProducer.sendProductBoughtMessage(convertToProductBoughtDTO(order));
            } else {
                checkoutService.updateOrderStatus(orderID, OrderState.REJECTED);
            }
        }
    }

    private ProductBoughtDTO convertToProductBoughtDTO(Order order) {
        Map<String, Integer> products = new HashMap<>();

        for (Watch watch : order.getProducts().keySet()) {
            products.put(watch.getId().toString(), watch.getOrderQuantity());
        }

        return new ProductBoughtDTO(products);
    }
}
