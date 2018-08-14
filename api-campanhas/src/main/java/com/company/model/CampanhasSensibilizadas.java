package com.company.model;

import com.company.entity.Campanha;

import java.util.ArrayList;
import java.util.List;

public class CampanhasSensibilizadas {

    private Campanha campanhaSalva;

    private List<Campanha> campanhasAfetadas = new ArrayList<>();

    public Campanha getCampanhaSalva() {
        return campanhaSalva;
    }

    public void setCampanhaSalva(Campanha campanhaSalva) {
        this.campanhaSalva = campanhaSalva;
    }

    public List<Campanha> getCampanhasAfetadas() {
        return campanhasAfetadas;
    }

    public void setCampanhasAfetadas(List<Campanha> campanhasAfetadas) {
        this.campanhasAfetadas = campanhasAfetadas;
    }

}
