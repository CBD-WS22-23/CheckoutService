package edu.timebandit.CheckoutService.port.config;

import edu.timebandit.CheckoutService.core.domain.model.Watch;
import edu.timebandit.CheckoutService.port.basket.dtos.WatchDTO;
import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
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
        TypeMap<WatchDTO, Watch> typeMap = mapper.createTypeMap(WatchDTO.class, Watch.class);
        Converter<String, java.util.UUID> toUUID = ctx -> ctx.getSource() == null ? null : java.util.UUID.fromString(ctx.getSource());
        typeMap.addMappings(m -> m.using(toUUID).map(WatchDTO::getId, Watch::setId));
        return mapper;
    }
}
