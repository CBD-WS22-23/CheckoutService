package edu.timebandit.CheckoutService.port.product.producer.interfaces;

import edu.timebandit.CheckoutService.port.product.dtos.ProductBoughtDTO;

public interface IProductBoughtProducer {
    void sendProductBoughtMessage(ProductBoughtDTO products);
}
