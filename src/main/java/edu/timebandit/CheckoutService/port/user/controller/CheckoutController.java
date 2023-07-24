package edu.timebandit.CheckoutService.port.user.controller;

import edu.timebandit.CheckoutService.core.domain.model.Order;
import edu.timebandit.CheckoutService.core.domain.service.interfaces.ICheckoutService;
import edu.timebandit.CheckoutService.port.user.exception.OrderNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cs/api/v1")
public class CheckoutController {

    @Autowired
    private ICheckoutService checkoutService;


    @Operation(summary = "Get all orders")
    @GetMapping(path = "/order")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Order> getAllOrders() {
        return checkoutService.getAllOrders();
    }

    @Operation(summary = "Get an order by id")
    @GetMapping(path = "/order/{orderID}")
    @ResponseStatus(HttpStatus.OK)
    public Order getOrder(@PathVariable String orderID) {
        Order requestedOrder = checkoutService.getOrderByID(orderID);
        if (requestedOrder == null) {
            throw new OrderNotFoundException(orderID);
        }

        return requestedOrder;
    }

    @Operation(summary = "Delete an order by id")
    @DeleteMapping(path = "/order/{orderID}/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteOrder(@PathVariable String orderID) {
        checkoutService.deleteOrder(orderID);
    }
}
