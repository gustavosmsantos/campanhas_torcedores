package com.company.entity.edge;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.company.entity.Campanha;
import com.company.entity.Time;

@Edge(value = "campanhas_times")
public class CampanhaTime {

    @From
    private Campanha campanha;

    @To
    private Time time;

}
