/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doc.num.projet;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import doc.num.projet.modele.*;
import javax.inject.Inject;



/**
 *
 * @author Solofo R.
 */
@RestController
public class AvionRestController {
    
     @Inject
    public AvionRepository avionRep;
    
    @Inject
    public MoteurRepository moteurRep;
    
    @RequestMapping(value = "/avion", method = RequestMethod.POST)
    public ResponseEntity<String> addAVion(@RequestBody Avion avion) {
        System.out.println(avion.getMoteur());
        moteurRep.save(avion.getMoteur());
        System.out.println("new moteur saved !");
        System.out.println(avion);
        avionRep.save(avion);
        System.out.println("new avion saved !");
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
