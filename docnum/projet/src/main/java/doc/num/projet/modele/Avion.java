package doc.num.projet.modele;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

/**
 * 
 * @author Nicolas T.
 */
@Entity
@Data
@JacksonXmlRootElement(localName = "Avion")
public class Avion implements Serializable {

    private static final long serialVersionUID = 1L;

    /* VARIABLES */

    /* Attributs de la balise <avion>*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JacksonXmlProperty(isAttribute = true)
    private Long id;

    /* Éléments de la balise <avion> */

    @JacksonXmlProperty
    private String name;

    @JacksonXmlProperty
    private int weight;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Moteur moteur;

    /* CONSTRUCTEURS */

    public Avion() {
    }

    public Avion(Long id, String name, int weight, Moteur moteur) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.moteur = moteur;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    

}
