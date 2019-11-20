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
import org.springframework.web.bind.annotation.RestController;

import doc.num.projet.modele.Avion;
import doc.num.projet.modele.AvionRepository;
import doc.num.projet.modele.MoteurRepository;

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

    @RequestMapping(value = "/avionAdd", method = RequestMethod.POST)
    public ResponseEntity<String> addAVion(@RequestBody Avion avion) {
        /** tout ce qui touche le moteur doit pouvoir être retiré
         *  repercutions en cascade (voir classe modifiée)
         */
        /*
        System.out.println(avion.getMoteur());
        moteurRep.save(avion.getMoteur());
        */
        System.out.println("new moteur saved !");
        System.out.println(avion);
        avionRep.save(avion);
        System.out.println("new avion saved !");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/avionUpdate/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateAVion(@PathVariable("id") Long id, @RequestBody Avion avion) {
        avionRep.findById(id);
       
        System.out.println(avion);
        avionRep.save(avion);
        System.out.println("new avion updated !");
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/avionDel/{id}")
    public void delAVion(@PathVariable("id") Long id) {
        avionRep.deleteById(id);
        System.out.println("avion deleted !");
    }

}
