package doc.num.projet;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import java.util.List;
import doc.num.projet.modele.*;

import javax.inject.Inject;

import lombok.var;


/**
 * index page controller
 */
@Controller
public class IndexController {
    
    @Inject
    MessageRepository messageRepo;
    
    @Inject
    AvionRepository avionRepo;

    @Inject
    MoteurRepository moteurRepo;

    @RequestMapping("/")
    public String indexAction(Model model) {
        model.addAttribute("messages", messageRepo.findAll());
        model.addAttribute("avions", avionRepo.findAll());
        model.addAttribute("moteurs", moteurRepo.findAll());
       return "index";
    }
}