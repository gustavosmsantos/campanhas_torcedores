package com.company.notification;

import com.company.entity.Campanha;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notificaAlteracaoCampanha(Campanha campanha) {
        //TODO: envio de mensagem para um tópico, onde sistemas interessados tenham listeners para notificação
        System.out.println(campanha);
    }

}
