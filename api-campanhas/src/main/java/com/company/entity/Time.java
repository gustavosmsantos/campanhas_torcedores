package com.company.entity;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Field;

@Document(value = "times")
public class Time {

    @Field
    private String nome;

}
