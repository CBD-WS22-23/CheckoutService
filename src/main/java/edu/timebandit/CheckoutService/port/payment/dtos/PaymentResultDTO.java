package edu.timebandit.CheckoutService.port.payment.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResultDTO {

        private String orderID;

        private String paymentID;

        private boolean paymentSuccessful;
}
