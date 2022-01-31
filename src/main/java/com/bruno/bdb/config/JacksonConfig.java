package com.bruno.bdb.config;

import com.bruno.bdb.dto.DepositDTO;
import com.bruno.bdb.dto.PaymentDTO;
import com.bruno.bdb.dto.TransferDTO;
import com.bruno.bdb.dto.WithdrawDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
            public void configure(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(DepositDTO.class);
                objectMapper.registerSubtypes(PaymentDTO.class);
                objectMapper.registerSubtypes(TransferDTO.class);
                objectMapper.registerSubtypes(WithdrawDTO.class);
                super.configure(objectMapper);
            }
        };
        return builder;
    }
}
