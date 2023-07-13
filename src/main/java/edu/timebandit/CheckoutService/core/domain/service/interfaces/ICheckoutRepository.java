package edu.timebandit.CheckoutService.core.domain.service.interfaces;

import edu.timebandit.CheckoutService.core.domain.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICheckoutRepository extends CrudRepository<Order, UUID> {
}
