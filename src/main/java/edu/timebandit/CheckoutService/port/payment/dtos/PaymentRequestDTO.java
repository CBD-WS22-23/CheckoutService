package edu.timebandit.CheckoutService.port.payment.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    @NotBlank(message = "Order id cannot be blank")
    private String orderID;
    @Positive(message = "Total price must be greater than 0")
    private double totalPrice;
    @NotBlank(message = "Payment method cannot be blank")
    private String paymentMethod;


}
