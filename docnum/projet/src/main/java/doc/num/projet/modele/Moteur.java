package doc.num.projet.modele;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

/**
 * 
 * @author Nicolas T.
 */
@Embeddable
@Data
@Entity
@Table(name = "MOTEUR")
@JacksonXmlRootElement(localName = "Moteur")
public class Moteur {

    /* VARIABLES */

    /* Attributs de la balise <moteur>*/

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    /* Identifiant unique de <moteur> */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* Éléments de la balise <moteur> */

    @JacksonXmlProperty
    private int puissance;
  
    @JacksonXmlProperty
    private int nombre;

    /* CONSTRUCTEURS */

    public Moteur() {

    }

    public Moteur(String type, int puissance, int nombre) {
        this.type = type;
        this.puissance = puissance;
        this.nombre = nombre;
    }

}
