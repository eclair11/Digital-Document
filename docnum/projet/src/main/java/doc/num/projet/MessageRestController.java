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
public class MessageRestController {
    
    @Inject
    MessageRepository messageRepo;
    
    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public ResponseEntity<String> addMessage(@RequestBody Message message) {
        messageRepo.save(message);
        System.out.println("un message a été ajouté !");
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
