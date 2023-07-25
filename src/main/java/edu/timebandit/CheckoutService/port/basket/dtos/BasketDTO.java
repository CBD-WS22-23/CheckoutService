package edu.timebandit.CheckoutService.port.basket.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketDTO {
    @NotBlank(message = "ID cannot be blank")
    private String id;
    @NotNull(message = "Address data cannot be null")
    private List<WatchDTO> watches;
    @PositiveOrZero(message = "Total price must be greater than or equal to 0")
    private double totalPrice;
}
