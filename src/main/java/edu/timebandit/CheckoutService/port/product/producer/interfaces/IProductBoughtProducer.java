package edu.timebandit.CheckoutService.port.product.producer.interfaces;

import edu.timebandit.CheckoutService.port.product.dtos.ProductBoughtDTO;
import jakarta.validation.Valid;

public interface IProductBoughtProducer {
    void sendProductBoughtMessage(@Valid ProductBoughtDTO products);
}
