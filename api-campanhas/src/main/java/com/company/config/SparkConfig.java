package com.company.config;

import com.company.routes.CampanhaRoutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static spark.Spark.after;

@Configuration
public class SparkConfig {

    @Autowired
    private CampanhaRoutes campanhaRoutes;

    @Bean
    public CommandLineRunner register() {
        return (args) -> {

            campanhaRoutes.register();

            //Definindo interceptor para retorno do tipo application/json
            after((request, response) -> response.type("application/json"));
        };
    }

}
