package edu.timebandit.CheckoutService.core.domain.model;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID basketID;

    private String paymentMethod;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID paymentID;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Watch, Double> products;

    private Double totalPrice;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "billing_first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "billing_last_name")),
            @AttributeOverride(name = "street", column = @Column(name = "billing_street")),
            @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "billing_zip_code")),
            @AttributeOverride(name = "state", column = @Column(name = "billing_state")),
            @AttributeOverride(name = "country", column = @Column(name = "billing_country"))

    })
    private Address billingAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "shipping_first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "shipping_last_name")),
            @AttributeOverride(name = "street", column = @Column(name = "shipping_street")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "shipping_zip_code")),
            @AttributeOverride(name = "state", column = @Column(name = "shipping_state")),
            @AttributeOverride(name = "country", column = @Column(name = "shipping_country"))
    })
    private Address shippingAddress;
}
