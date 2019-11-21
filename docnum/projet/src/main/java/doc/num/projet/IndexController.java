package doc.num.projet;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import doc.num.projet.modele.AvionRepository;
import doc.num.projet.modele.MessageRepository;
import doc.num.projet.modele.MoteurRepository;

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