package com.company.entity;

import com.arangodb.entity.BaseDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(value = {"id", "revision", "properties"}, ignoreUnknown = true)
public class Campanha extends BaseDocument {

    private String nome;

    private LocalDate inicio;

    private LocalDate fim;

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

    @Override
    public String toString() {
        return "Campanha{" +
                "nome='" + nome + '\'' +
                ", inicio=" + inicio +
                ", fim=" + fim +
                ", id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", revision='" + revision + '\'' +
                ", properties=" + properties +
                '}';
    }
}
