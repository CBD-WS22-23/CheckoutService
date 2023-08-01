package edu.timebandit.CheckoutService.port.basket.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;



class OrderDTOValidationTest {

    @Test
    void givenValidDTO_whenValidating_thenReceiveNoViolations() {
        //Given
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        BasketDTO basketDTO = new BasketDTO("basketID", new ArrayList<>(), 1);
        AddressDTO addressDTO = new AddressDTO("firstName", "lastName", "street",
                "city", "zipCode", "state", "country");

        //When
        OrderDTO orderDTO = new OrderDTO(basketDTO, addressDTO, addressDTO, "paymentMethod");

        //Then
        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);
        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    void givenInvalidDTO_whenValidating_thenReceiveConstrainViolations() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        BasketDTO basketDTO = new BasketDTO("", null, -1);
        AddressDTO addressDTO = new AddressDTO("", "", "",
                "", "", "", "");
        OrderDTO orderDTO = new OrderDTO(basketDTO, addressDTO, addressDTO, "");

        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);

        assertThat(violations.size()).isNotZero();
    }
}