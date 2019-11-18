/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doc.num.projet.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import doc.num.projet.modele.Avion;
import doc.num.projet.modele.Message;

/**
 *
 * @author Solofo R.
 */
@Service
public class RestClient {

    private RestTemplate restTemplate = new RestTemplate();

    public void addNewAvion(Avion avion) {
        this.restTemplate.postForObject("http://localhost:8080/avion", avion, Avion.class);
    }

    public void addNewMessage(Message message) {
        this.restTemplate.postForObject("http://localhost:8080/message", message, Message.class);
    }
}
