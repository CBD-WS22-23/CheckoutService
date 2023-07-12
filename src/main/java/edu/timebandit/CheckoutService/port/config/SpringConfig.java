package edu.timebandit.CheckoutService.port.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    @Qualifier("ModelMapper")
    public org.modelmapper.ModelMapper modelMapper() {
        return new org.modelmapper.ModelMapper();
    }

    @Bean
    @Qualifier("CheckoutModelMapper")
    public org.modelmapper.ModelMapper CheckoutModelMapper() {
        org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();

        return mapper;
    }
}
