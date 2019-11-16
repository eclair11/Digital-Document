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
@JsonPropertyOrder({ "id", "name", "weight"})
public class Action {

    /* A écrire .................................................. */

    @JacksonXmlProperty
    private String intitule; /* lui n'a normalement pas à être sauvegardé */

    @JacksonXmlProperty(localName = "avion")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Avion> avion;

    
}