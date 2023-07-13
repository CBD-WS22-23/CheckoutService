package edu.timebandit.CheckoutService.core.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.Accessors;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Builder
public class Order {

    @Id
    @Column(nullable = false, unique = true)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    private OrderState status;

    private UUID basketID;

    private String paymentMethod;

    private UUID paymentID;

    @ElementCollection
    private Map<Watch, Double> products;

    private Double totalPrice;

    private Address billingAddress;

    private Address shippingAddress;
}
