package doc.num.projet;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

/**
 * Action
 */
@Data
@JsonPropertyOrder({ "intitule", "id", "name", "weight"}) /* à modifier probablement */
public class Action {

    /* A écrire .................................................. */

    @JacksonXmlProperty
    private int intitule; /* lui n'a normalement pas à être sauvegardé */

    @JacksonXmlProperty(localName = "avion")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Avion> avion;

    public Action() {
    }

    public Action(int intitule, List<Avion> avion) {
        this.intitule = intitule;
        this.avion = avion;
    }
    
}