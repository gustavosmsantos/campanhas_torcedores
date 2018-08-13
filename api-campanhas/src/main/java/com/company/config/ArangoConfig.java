package com.company.config;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.velocypack.VPackDeserializer;
import com.arangodb.velocypack.VPackSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

import static com.company.utils.ApplicationUtils.df;

@Configuration
public class ArangoConfig {

    @Value("${arangodb.database}")
    private String arangoDatabase;

    @Bean
    public ArangoDatabase getDatabase() {
        VPackDeserializer<LocalDate> deserializer = (parent, vpack, context) -> LocalDate.parse(vpack.getAsString(), df);
        VPackSerializer<LocalDate> serializer = (builder, attribute, value, context) -> builder.add(attribute, df.format(value));
        return new ArangoDB.Builder()
                .registerSerializer(LocalDate.class, serializer)
                .registerDeserializer(LocalDate.class, deserializer)
                .build().db(arangoDatabase);
    }

}