package doc.num.projet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * index page controller
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String indexAction() {
        return "redirect:/index.html";
    }
}