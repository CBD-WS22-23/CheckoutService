package edu.timebandit.CheckoutService.port.basket.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchDTO {
    @NotBlank(message = "ID cannot be blank")
    private String id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @PositiveOrZero(message = "Price must be greater than or equal to 0")
    private double price;
    @Min(value = 1, message = "Order quantity must be greater than 0")
    private int orderQuantity;
}
