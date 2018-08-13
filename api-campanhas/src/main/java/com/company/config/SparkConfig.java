package com.company.config;

import com.company.exception.ValidationException;
import com.company.routes.CampanhaRoutes;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.company.utils.ApplicationUtils.mapper;
import static spark.Spark.after;
import static spark.Spark.exception;

@Configuration
public class SparkConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SparkConfig.class);

    @Autowired
    private CampanhaRoutes campanhaRoutes;

    @Bean
    public CommandLineRunner register() {
        return (args) -> {

            exception(ValidationException.class, (ex, req, res) -> {
                res.status(400);
                res.body(errorMessage(ex.getMessage()));
            });

            exception(Exception.class, (ex, req, res) -> {
                res.status(500);
                LOGGER.error("Falha inesperada", ex);
                res.body(errorMessage("Falha inesperada na aplicação"));
            });

            campanhaRoutes.register();

            //Definindo interceptor para retorno do tipo application/json
            after((request, response) -> response.type("application/json"));
        };
    }

    private String errorMessage(String message) {
        ObjectNode node = mapper.createObjectNode();
        node.put("message", message);
        return node.toString();
    }

}
