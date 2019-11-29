/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doc.num.projet.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import doc.num.projet.modele.Avion;
import doc.num.projet.modele.Memoire;
import doc.num.projet.modele.Message;

/**
 *
 * @author Solofo R.
 * @author Nicolas T.
 */
@Service
public class RestClient {

    /** méthodes à renommer
     *
     * ça veut rien dire de delete NewAvion,
     * ou add NewAvion
     * on fera ça plus tard! Trop de travail!!
     */

    private RestTemplate restTemplate = new RestTemplate();

    public void addNewAvion(Avion avion, Long id) {
        this.restTemplate.postForObject("http://localhost:8080/avionAdd/" + id, avion, Avion.class);
    }

    public void updateNewAvion(Avion avion, Long id) {
        this.restTemplate.put("http://localhost:8080/avionUpdate/" + id, avion, Avion.class);
    }

    public void deleteNewAvion(Avion avion, Long id) {
        this.restTemplate.delete("http://localhost:8080/avionDel/" + id, id, avion);
    }

    public void addNewMessage(Message message) {
        this.restTemplate.postForObject("http://localhost:8080/message", message, Message.class);
    }

	public void addNewMemoire(Memoire memoire) {
        this.restTemplate.postForObject("http://localhost:8080/avionLog", memoire, Memoire.class);
    }
    
    public String checkMemoire(String idFic) {
       return  this.restTemplate.postForObject("http://localhost:8080/checkLog", idFic, String.class);
	}
}
