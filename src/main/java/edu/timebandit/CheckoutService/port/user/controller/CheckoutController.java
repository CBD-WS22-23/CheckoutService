package edu.timebandit.CheckoutService.port.user.controller;

import edu.timebandit.CheckoutService.core.domain.model.Order;
import edu.timebandit.CheckoutService.core.domain.service.interfaces.ICheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CheckoutController {

    @Autowired
    private ICheckoutService checkoutService;


    @Operation(summary = "Get all orders")
    @GetMapping(path = "/order")
    public Iterable<Order> getAllOrders() {
        return checkoutService.getAllOrders();
    }

    @Operation(summary = "Get an order by id")
    @GetMapping(path = "/order/{orderID}")
    public Order getOrder(@PathVariable String orderID) {
        return checkoutService.getOrderByID(orderID);
    }

    @Operation(summary = "Delete an order by id")
    @DeleteMapping(path = "/order/{orderID}/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteOrder(@PathVariable String orderID) {
        checkoutService.deleteOrder(orderID);
    }
}
