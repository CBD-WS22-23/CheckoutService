package edu.timebandit.CheckoutService.port.basket.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private BasketDTO basket;
    private AddressDTO shippingAddress;
    private AddressDTO billingAddress;
    private String paymentMethod;
}
