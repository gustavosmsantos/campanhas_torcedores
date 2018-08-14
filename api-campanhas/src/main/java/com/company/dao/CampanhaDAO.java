package com.company.dao;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseEdgeDocument;
import com.arangodb.model.TransactionOptions;
import com.company.entity.Campanha;
import com.company.model.CampanhasSensibilizadas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.company.utils.ApplicationUtils.df;


@Component
public class CampanhaDAO {

    @Autowired
    private ArangoDatabase arangoDb;

    public static final String CAMPANHAS_COLLECTION = "campanhas";

    private static final String CAMPANHAS_TIMES_COLLECTIONS = "campanhas_times";

    public List<Campanha> findActiveInDate(LocalDate date) {
        String now = date.format(df);
        String query = "FOR c IN campanhas FILTER c.`inicio` <= @now AND c.`fim` >= @now RETURN c";
        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("now", now);
        ArangoCursor<Campanha> campanhaCursor = arangoDb.query(query, bindVars, Campanha.class);
        return campanhaCursor.asListRemaining();
    }

    public List<Campanha> findInBetween(LocalDate inicio, LocalDate fim) {
        String query = "FOR c IN campanhas FILTER c.`inicio` <= @inicio AND c.`fim` >= @fim RETURN c";
        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("inicio", df.format(inicio));
        bindVars.put("fim", df.format(fim));
        ArangoCursor<Campanha> campanhaCursor = arangoDb.query(query, bindVars, Campanha.class);
        return campanhaCursor.asListRemaining();
    }

    public CampanhasSensibilizadas gravaCampanha(Campanha campanha, String timeId) {
        try {
            String query = new String(Files.readAllBytes(Paths.get(getClass().getResource("/gravacao_campanha.js").toURI())));
            TransactionOptions options = new TransactionOptions();
            options.readCollections("campanhas");
            options.writeCollections("campanhas", "campanhas_times");

            HashMap<String, Object> p = new HashMap<>();
            p.put("campanha", campanha);
            p.put("timeId", timeId);
            options.params(p);

            CampanhasSensibilizadas campanhas = arangoDb.transaction(query, CampanhasSensibilizadas.class, options);
            return campanhas;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void associaTime(String idCampanha, String idTime) {
        BaseEdgeDocument edgeDocument = new BaseEdgeDocument();
        edgeDocument.setFrom(idCampanha);
        edgeDocument.setTo(idTime);
        arangoDb.collection(CAMPANHAS_TIMES_COLLECTIONS).insertDocument(edgeDocument);
    }

    public void save(Campanha campanha) {
        arangoDb.collection(CAMPANHAS_COLLECTION).insertDocument(campanha);
    }

    public void update(Campanha campanha) {
        arangoDb.collection(CAMPANHAS_COLLECTION).updateDocument(campanha.getKey(), campanha);
    }

    public void delete(String campanhaKey) {
        arangoDb.collection(CAMPANHAS_COLLECTION).deleteDocument(campanhaKey);
        deleteEdges(CAMPANHAS_TIMES_COLLECTIONS, campanhaKey);
    }

    private void deleteEdges(String edgeName, String campanhaKey) {
        HashMap<String, Object> bindVars = new HashMap<>();
        bindVars.put("from", CAMPANHAS_COLLECTION + "/" + campanhaKey);
        ArangoCollection collection = arangoDb.collection(edgeName);
        String query = "FOR e IN " + edgeName + " FILTER e.`_from` == @from RETURN e.`_key`";
        ArangoCursor<String> queryExecution = arangoDb.query(query, bindVars, String.class);
        List<String> edges = queryExecution.asListRemaining();
        for (String edge : edges) {
            collection.deleteDocument(edge);
        }
    }

}
