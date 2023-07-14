package edu.timebandit.CheckoutService.port.basket.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchDTO {
    private String id;
    private String name;
    private double price;
    private int orderQuantity;
}
