package doc.num.projet.modele;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * 
 * @author Nicolas T.
 */
@Data
@JsonPropertyOrder({ "id", "name", "weight", "moteur" })
public class Action {

    /* VARIABLES */

    /* Attributs de la balise <action>*/

    /* type d'actione effectuée */
    @JacksonXmlProperty(isAttribute = true)
    private String intitule; /* lui n'a normalement pas à être sauvegardé */

    /**
     * id unique du fichier
     * un fichier ne peut être employé plus d'une fois
     */
    @JacksonXmlProperty(isAttribute = true)
    private String idFic;

    /**
     * nombre d'informations déclarées par action 
     * qui doit correspondre avec le nb réel 
     */
    @JacksonXmlProperty(isAttribute = true)
    private int checksum;

    @JacksonXmlProperty(isAttribute = true)
    @Temporal(TemporalType.DATE)
    private Date dateAction = new Date();


    /*Éléments de la balise <action> */

    @JacksonXmlProperty(localName = "avion")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Avion> avion;

    /* CONSTRUCTEURS */

    public Action() {
    }

    public Action(String intitule, String idFic, int checksum, Date dateAction) {
        this.intitule = intitule;
        this.idFic = idFic;
        this.checksum = checksum;
        this.dateAction = dateAction;
    }

}
