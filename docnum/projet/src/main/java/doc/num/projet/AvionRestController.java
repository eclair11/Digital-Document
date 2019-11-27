/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doc.num.projet;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import doc.num.projet.modele.Avion;
import doc.num.projet.modele.Moteur;
import doc.num.projet.modele.Message;
import doc.num.projet.modele.AvionRepository;
import doc.num.projet.modele.Memoire;
import doc.num.projet.modele.MemoireRepository;
import doc.num.projet.modele.MoteurRepository;
import doc.num.projet.modele.MessageRepository;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Solofo R.
 * @author Nicolas T.
 */
@RestController
public class AvionRestController {

    @Inject
    public AvionRepository avionRep;

    @Inject
    public MoteurRepository moteurRep;

    @Inject
    public MessageRepository messageRep;

    @Inject
    public MemoireRepository memoireRep;

    @RequestMapping(value = "/avions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Avion[] getAllAvion() {
        List<Avion> avions = new ArrayList<Avion>();
        for (Avion avion : avionRep.findAll()) {
            avions.add(avion);
        }
        Avion[] avionArr = new Avion[avions.size()];
        avionArr = avions.toArray(avionArr);
        return avionArr;
    }

    @RequestMapping(value = "/moteurs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Moteur[] getAllMoteur() {
        List<Moteur> moteurs = new ArrayList<Moteur>();
        for (Moteur moteur : moteurRep.findAll()) {
            moteurs.add(moteur);
        }
        Moteur[] moteurArr = new Moteur[moteurs.size()];
        moteurArr = moteurs.toArray(moteurArr);
        return moteurArr;
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Message[] getAllMessage() {
        List<Message> messages = new ArrayList<Message>();
        for (Message message : messageRep.findAll()) {
            messages.add(message);
        }
        Message[] messageArr = new Message[messages.size()];
        messageArr = messages.toArray(messageArr);
        return messageArr;
    }

    @RequestMapping(value = "/avionLog", method = RequestMethod.POST)
    public ResponseEntity<String> addMemoire(@RequestBody Memoire memoire) {
        memoireRep.save(memoire);
        System.out.println("idfic = " + memoire.getIdFic());
        System.out.println("new log saved in memory!");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/avionAdd/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> addAVion(@PathVariable("id") Long id, @RequestBody Avion avion, Memoire memoire) {
        /**
         * tout ce qui touche le moteur doit pouvoir être retiré repercutions en cascade
         * (voir classe modifiée)
         */
        /*
         * System.out.println(avion.getMoteur()); moteurRep.save(avion.getMoteur());
         * System.out.println("new moteur saved !");
         */

        // int nbAvion = (int) avionRep.count();

        Iterable<Avion> avionIterator = avionRep.findAll();

        for (Avion a : avionIterator) {
            System.out.println("voici l'Id = " + a.getId());
            if (a.getName().matches(avion.getName())) {

                System.out.println(avion);
                System.out.println("Avion déjà présent");
                return new ResponseEntity(HttpStatus.FOUND);
            }

        }

        System.out.println(avion);
        avionRep.save(avion);
        System.out.println("new avion saved !");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/avionUpdate/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateAVion(@PathVariable("id") Long id, @RequestBody Avion avion) {
        avionRep.findById(id);

        int nbAvion = (int) avionRep.count();

        /*
        if ( avionRep.findById(id).get().getName().matches(avion.getName()) ) {
            System.out.println(avion);
            System.out.println("Avion déjà présent");
            return new ResponseEntity(HttpStatus.OK);
        }
        */

        /* on vérifie que l'avion à modifier est bien présent dans  */
        if (avionRep.findById(id).get().getId()  != avion.getId()) {
            System.out.println(avion);
            System.out.println("UPDATE suspendu: L'avion à mettre à jour n'est pas présent dans la BDD");
            return new ResponseEntity(HttpStatus.FOUND);
        }


        avionRep.save(avion);
        System.out.println(avion);
        System.out.println("new avion updated !");
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/avionDel/{id}")
    public void delAVion(@PathVariable("id") Long id) {
        avionRep.deleteById(id);
        System.out.println("avion deleted !");
    }

}
