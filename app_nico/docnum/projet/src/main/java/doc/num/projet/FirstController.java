package doc.num.projet;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

import lombok.var;


/**
 * FirstController
 */
@Controller
public class FirstController {

    @Inject
    public AvionRepository avionRep;

    

    @RequestMapping("/home")
    public String index(){
        return "index";
    }

    /* De BDD vers XML */
    @ResponseBody
    @RequestMapping(value = "/serializeXML", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public Iterable<Avion> SerializeXMLFlotte() {
        return avionRep.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/serializeXML/{avionId}", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public Avion SerializeXMLAvion(@PathVariable Long avionId){
        var avion = avionRep.findById(avionId).get();
        return avion;
    }

    @RequestMapping(value="/deserializationJackson")
    public String DeserializationJackson(){
        ParserJacksonXml.Deserialize();
        return "redirect:/home";
    } 

    @RequestMapping(value="/deserializationDOM")
    public String DeserializationDOM(){
        ParserDomJava.ParserXMLDomJava();
        return "redirect:/home";
    }

    @RequestMapping(value="/deserializationSAX")
    public String DeserializationSAX(){
        ParserSaxJava.ParserXMLSaxJava();
        return "redirect:/home";
    }

}