package com.company.dao;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.company.entity.Campanha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.company.utils.ApplicationUtils.df;

@Component
public class CampanhaDAO {

    @Autowired
    private ArangoDatabase arangoDb;

    private static final String COLLECTION_NAME = "campanhas";

    public List<Campanha> findActiveInDate(LocalDate date) {
        String now = date.format(df);
        String query = "FOR c IN campanhas FILTER c.`inicio` <= @now AND c.`fim` >= @now RETURN c";
        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("now", now);
        ArangoCursor<Campanha> campanhaCursor = arangoDb.query(query, bindVars, Campanha.class);
        return campanhaCursor.asListRemaining();
    }

    public void save(Campanha campanha) {
        try {
            arangoDb.collection(COLLECTION_NAME).insertDocument(campanha);
        } catch (ArangoDBException e) {
            e.printStackTrace();
        }
    }

}
