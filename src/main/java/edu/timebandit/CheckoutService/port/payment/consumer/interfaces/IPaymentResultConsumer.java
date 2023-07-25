package edu.timebandit.CheckoutService.port.payment.consumer.interfaces;

import edu.timebandit.CheckoutService.port.payment.dtos.PaymentResultDTO;

public interface IPaymentResultConsumer {
    void receivePaymentResultMessage(PaymentResultDTO paymentResult);
}
