package com.Barbearia.BarbeariaProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAIConfig {

    @Bean
    public OpenAPI customApi(){
        return new OpenAPI()
                    .info(new Info()
                    .title("API - Gerenciamento Agendamento Barbearia")
                    .version("1.0")
                    .description("Documentação para a API de Gestão de Agendamentos de uma Barbearia"));
    }
}
