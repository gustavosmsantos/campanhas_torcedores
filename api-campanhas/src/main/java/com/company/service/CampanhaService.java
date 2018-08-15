package com.company.service;

import com.company.dao.CampanhaDAO;
import com.company.entity.Campanha;
import com.company.exception.ValidationException;
import com.company.model.CampanhasSensibilizadas;
import com.company.notification.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CampanhaService {

    @Autowired
    private CampanhaDAO campanhaDAO;

    @Autowired
    private NotificationService notificationService;

    public Campanha novaCampanha(String nome, String timeId, LocalDate inicio, LocalDate fim) throws ValidationException {
        if (inicio == null || fim == null) {
            throw new ValidationException("Os parâmetros \"inicio\" e \"fim\" devem ser informados");
        }

        if (inicio.isAfter(fim)) {
            throw new ValidationException("A data de início deve ser posterior à data de fim");
        }

        //TODO: validar se time existe

        Campanha campanha = campanha(nome, inicio, fim);
        CampanhasSensibilizadas campanhasSensibilizadas = campanhaDAO.save(campanha, timeId);
        campanhasSensibilizadas.getCampanhasAfetadas().forEach(c -> notificationService.notificaAlteracaoCampanha(c));

        return campanhasSensibilizadas.getCampanhaSalva();
    }

    public void atualizaCampanha(String campanhaKey, Campanha campanha) {
        campanha.setKey(campanhaKey);
        try {
            this.campanhaDAO.update(campanha);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Campanha> buscaCampanhasAtivas() {
        return this.campanhaDAO.findActiveInDate(LocalDate.now());
    }

    public void excluiCampanha(String campanhaKey) {
        this.campanhaDAO.delete(campanhaKey);
    }

    private Campanha campanha(String nome, LocalDate inicio, LocalDate fim) {
        Campanha campanha = new Campanha();
        campanha.setNome(nome);
        campanha.setInicio(inicio);
        campanha.setFim(fim);
        return campanha;
    }

}