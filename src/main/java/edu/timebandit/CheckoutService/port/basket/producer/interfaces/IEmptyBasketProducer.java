package edu.timebandit.CheckoutService.port.basket.producer.interfaces;

public interface IEmptyBasketProducer {
    void sendEmptyBasketMessage(String basketID);
}
