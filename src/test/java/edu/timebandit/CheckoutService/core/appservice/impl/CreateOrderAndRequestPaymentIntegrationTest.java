package edu.timebandit.CheckoutService.core.appservice.impl;

import edu.timebandit.CheckoutService.core.domain.model.Order;
import edu.timebandit.CheckoutService.core.domain.service.impl.CheckoutService;
import edu.timebandit.CheckoutService.port.basket.dtos.AddressDTO;
import edu.timebandit.CheckoutService.port.basket.dtos.BasketDTO;
import edu.timebandit.CheckoutService.port.basket.dtos.OrderDTO;
import edu.timebandit.CheckoutService.port.config.SpringConfig;
import edu.timebandit.CheckoutService.port.payment.producer.impl.PaymentRequestProducer;
import edu.timebandit.CheckoutService.port.payment.producer.interfaces.IPaymentRequestProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = {CreateOrderAndRequestPayment.class, SpringConfig.class, CheckoutService.class,
        PaymentRequestProducer.class})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "edu.timebandit.CheckoutService.core.domain.service.interfaces")
@EntityScan(basePackages = "edu.timebandit.CheckoutService.core.domain.model")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ExtendWith(MockitoExtension.class)
class CreateOrderAndRequestPaymentIntegrationTest {

    @Autowired
    private CheckoutService checkoutService;

    @Mock
    private IPaymentRequestProducer paymentRequestProducer;


    @InjectMocks
    @Autowired
    private CreateOrderAndRequestPayment createOrderAndRequestPayment;

    @Test
    void givenValidDTO_whenCreateAndRequest_thenCreateAndSaveOrderFromDTO() {
        //Given
        Mockito.doNothing().when(paymentRequestProducer).sendPaymentRequestMessage(Mockito.any());
        BasketDTO basketDTO = new BasketDTO("6ba94a65-836d-4a9a-a5cc-4090bd3adbee", new ArrayList<>(), 1123.12);
        AddressDTO addressDTO = new AddressDTO("firstName", "lastName", "street",
                "city", "zipCode", "state", "country");
        OrderDTO orderDTO = new OrderDTO(basketDTO, addressDTO, addressDTO, "paymentMethod");

        //When
        createOrderAndRequestPayment.createAndRequest(orderDTO);

        //Then
        Iterable<Order> retrievedOrders= checkoutService.getAllOrders();
        List<Order> orders = new ArrayList<>();
        retrievedOrders.forEach(orders::add);

        assertEquals(1, orders.size());
        assertEquals("6ba94a65-836d-4a9a-a5cc-4090bd3adbee", orders.get(0).getBasketID().toString());
        assertEquals(0, orders.get(0).getProducts().size());
        assertEquals("firstName", orders.get(0).getShippingAddress().getFirstName());
        assertEquals("lastName", orders.get(0).getShippingAddress().getLastName());
        assertEquals(1123.12, orders.get(0).getTotalPrice());
        assertEquals("paymentMethod", orders.get(0).getPaymentMethod());
    }
}