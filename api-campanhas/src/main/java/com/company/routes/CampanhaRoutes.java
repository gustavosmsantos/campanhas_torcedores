package com.company.routes;


import com.company.routes.transformer.JsonTransformer;
import com.company.service.CampanhaService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.company.utils.ApplicationUtils.df;
import static com.company.utils.ApplicationUtils.mapper;
import static spark.Spark.*;

@Component
public class CampanhaRoutes {

    @Autowired
    private CampanhaService campanhaService;

    private static JsonTransformer jsonTransformer = new JsonTransformer();

    public void register() {

        get("/campanhas", (req, res) -> campanhaService.buscaCampanhasAtivas(), jsonTransformer);

        post("/campanhas", (req, res) -> {
            String body = req.body();
            JsonNode jsonNode = mapper.readTree(body);

            String nome = jsonNode.at("/nome").asText();
            String timeId = jsonNode.at("/timeId").asText();
            String inicio = jsonNode.at("/inicio").asText();
            String fim = jsonNode.at("/fim").asText();

            return campanhaService.novaCampanha(nome, timeId, LocalDate.parse(inicio, df), LocalDate.parse(fim, df));
        }, jsonTransformer);

        delete("/campanhas/:campanhaKey", (req, res) -> {
            String campanha = req.params("campanhaKey");
            campanhaService.excluiCampanha(campanha);
            return "{}";
        });

    }

}
