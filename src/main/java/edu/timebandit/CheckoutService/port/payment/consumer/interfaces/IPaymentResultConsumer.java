package edu.timebandit.CheckoutService.port.payment.consumer.interfaces;

import edu.timebandit.CheckoutService.port.payment.dtos.PaymentResultDTO;
import jakarta.validation.Valid;

public interface IPaymentResultConsumer {
    void receivePaymentResultMessage(@Valid PaymentResultDTO paymentResult);
}
