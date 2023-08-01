package edu.timebandit.CheckoutService.port.basket.consumer.impl;

import edu.timebandit.CheckoutService.core.appservice.impl.CreateOrderAndRequestPayment;
import edu.timebandit.CheckoutService.core.domain.service.impl.CheckoutService;
import edu.timebandit.CheckoutService.port.basket.dtos.AddressDTO;
import edu.timebandit.CheckoutService.port.basket.dtos.BasketDTO;
import edu.timebandit.CheckoutService.port.basket.dtos.OrderDTO;
import edu.timebandit.CheckoutService.port.config.SpringConfig;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = { SpringConfig.class, CheckoutService.class,
        CreateOrderConsumer.class})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "edu.timebandit.CheckoutService.core.domain.service.interfaces")
@EntityScan(basePackages = "edu.timebandit.CheckoutService.core.domain.model")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ExtendWith(MockitoExtension.class)
class CreateOrderConsumerValidationTest {

    @MockBean
    private CreateOrderAndRequestPayment createOrderAndRequestPayment;

    @Autowired
    private CreateOrderConsumer createOrderConsumer;


    @Test
    void givenInvalidDTO_whenReceiveMessage_thenThrowException() {
        //Given&When&Then
        assertThrows(ConstraintViolationException.class, () -> createOrderConsumer.receiveCreateOrderMessage(new OrderDTO()));
    }

    @Test
    void givenValidDTO_whenReceiveMessage_thenNoException() {
        //Given
        Mockito.doNothing().when(createOrderAndRequestPayment).createAndRequest(Mockito.any());
        BasketDTO basketDTO = new BasketDTO("6ba94a65-836d-4a9a-a5cc-4090bd3adbee", new ArrayList<>(), 0);
        AddressDTO addressDTO = new AddressDTO("firstName", "lastName", "street",
                "city", "zip","state", "country");
        OrderDTO orderDTO = new OrderDTO(basketDTO, addressDTO, addressDTO, "paymentMethod");

        assertDoesNotThrow(() -> createOrderConsumer.receiveCreateOrderMessage(orderDTO));
    }

}