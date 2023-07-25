package edu.timebandit.CheckoutService.core.appservice.interfaces;

import edu.timebandit.CheckoutService.port.payment.dtos.PaymentResultDTO;

public interface IProcessPaymentResultAndNotifyServices {
    void processResultAndNotify(PaymentResultDTO paymentResult);
}
