package edu.timebandit.CheckoutService.port.payment.producer.interfaces;

import edu.timebandit.CheckoutService.port.payment.dtos.PaymentRequestDTO;
import jakarta.validation.Valid;

public interface IPaymentRequestProducer {
    void sendPaymentRequestMessage(@Valid PaymentRequestDTO paymentDTO);
}
