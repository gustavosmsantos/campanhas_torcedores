package com.company.config;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.AbstractArangoConfiguration;
import com.arangodb.velocypack.VPackDeserializer;
import com.arangodb.velocypack.VPackSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

import static com.company.utils.ApplicationUtils.df;

@Configuration
@EnableArangoRepositories(basePackages = {"com.company"})
public class ArangoConfig extends AbstractArangoConfiguration {

    @Value("${arangodb.database}")
    private String arangoDatabase;

    @Override
    protected ArangoDB.Builder arango() {
        VPackDeserializer<LocalDate> deserializer = (parent, vpack, context) -> LocalDate.parse(vpack.getAsString(), df);
        VPackSerializer<LocalDate> serializer = (builder, attribute, value, context) -> builder.add(attribute, df.format(value));
        return new ArangoDB.Builder()
                .registerSerializer(LocalDate.class, serializer)
                .registerDeserializer(LocalDate.class, deserializer);
    }

    @Override
    protected String database() {
        return arangoDatabase;
    }

}