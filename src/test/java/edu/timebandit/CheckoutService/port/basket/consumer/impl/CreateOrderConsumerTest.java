package edu.timebandit.CheckoutService.port.basket.consumer.impl;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import edu.timebandit.CheckoutService.core.appservice.interfaces.ICreateOrderAndRequestPayment;
import edu.timebandit.CheckoutService.port.basket.dtos.OrderDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ch.qos.logback.classic.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CreateOrderConsumerTest {
    @Mock
    private ICreateOrderAndRequestPayment createOrderAndRequestPayment;
    @InjectMocks
    private CreateOrderConsumer createOrderConsumer;

    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeEach
    void setUp() {
        Mockito.doNothing().when(createOrderAndRequestPayment).createAndRequest(Mockito.any());
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(CreateOrderConsumer.class)).addAppender(logWatcher);
    }

    @AfterEach
    void tearDown() {
        ((Logger) LoggerFactory.getLogger(CreateOrderConsumer.class)).detachAndStopAllAppenders();
    }


    @Test
    void givenDTO_whenReceiveMessage_thenCorrectLoggerOutput() {
        //Given
        OrderDTO orderDTO = new OrderDTO();

        //When
        createOrderConsumer.receiveCreateOrderMessage(orderDTO);

        //Then
        assertThat(logWatcher.list.get(0).getFormattedMessage()).isEqualTo("Received message to create order: " + orderDTO);
    }
}
