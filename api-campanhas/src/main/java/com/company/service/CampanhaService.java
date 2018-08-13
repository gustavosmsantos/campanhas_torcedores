package com.company.service;

import com.company.dao.CampanhaDAO;
import com.company.entity.Campanha;
import com.company.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CampanhaService {

    @Autowired
    private CampanhaDAO campanhaDAO;

    public Campanha novaCampanha(String nome, String timeId, LocalDate inicio, LocalDate fim) throws ValidationException {
        if (inicio == null || fim == null) {
            throw new ValidationException("Os parâmetros \"inicio\" e \"fim\" devem ser informados");
        }

        if (inicio.isAfter(fim)) {
            throw new ValidationException("A data de início deve ser posterior à data de fim");
        }

        //TODO: validar se time existe

        Campanha campanha = campanha(nome, inicio, fim);
        campanhaDAO.save(campanha);
        campanhaDAO.associaTime(campanha.getId(), timeId);

        return campanha;
    }

    public List<Campanha> buscaCampanhasAtivas() {
        return this.campanhaDAO.findActiveInDate(LocalDate.now());
    }

    private Campanha campanha(String nome, LocalDate inicio, LocalDate fim) {
        Campanha campanha = new Campanha();
        campanha.setNome(nome);
        campanha.setInicio(inicio);
        campanha.setFim(fim);
        return campanha;
    }

}