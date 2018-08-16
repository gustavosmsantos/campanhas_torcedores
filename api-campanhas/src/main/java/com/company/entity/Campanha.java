package com.company.entity;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Field;
import com.arangodb.springframework.annotation.From;
import com.company.entity.edge.CampanhaTime;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@Document(value = "campanhas")
public class Campanha {

    @Id
    private String id;

    @Field
    private String nome;

    @Field
    private LocalDate inicio;

    @Field
    private LocalDate fim;

    @From
    private List<CampanhaTime> campanhasTimes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public LocalDate getFim() {
        return fim;
    }

}
