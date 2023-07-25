package edu.timebandit.CheckoutService.port.product.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBoughtDTO {
    @NotNull(message = "Bought products cannot be null")
    Map<String, Integer> boughtProducts;
}
