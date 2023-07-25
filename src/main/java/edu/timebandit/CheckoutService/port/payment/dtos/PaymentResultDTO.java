package edu.timebandit.CheckoutService.port.payment.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResultDTO {
        @NotBlank(message = "Order id cannot be blank")
        private String orderID;
        @NotBlank(message = "Payment id cannot be blank")
        private String paymentID;

        private boolean paymentSuccessful;
}
