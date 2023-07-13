package edu.timebandit.CheckoutService.port.basket.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String firstName;

    private String lastName;

    private String street;

    private String city;

    private String zipCode;

    private String state;

    private String country;
}
