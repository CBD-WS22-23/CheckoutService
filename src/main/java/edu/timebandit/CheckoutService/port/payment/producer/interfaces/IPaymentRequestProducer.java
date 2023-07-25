package edu.timebandit.CheckoutService.port.payment.producer.interfaces;

import edu.timebandit.CheckoutService.port.payment.dtos.PaymentRequestDTO;

public interface IPaymentRequestProducer {
    void sendPaymentRequestMessage(PaymentRequestDTO paymentDTO);
}
