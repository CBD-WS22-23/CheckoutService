package edu.timebandit.CheckoutService.port.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBoughtDTO {

    Map<String, Integer> boughtProducts;
}
