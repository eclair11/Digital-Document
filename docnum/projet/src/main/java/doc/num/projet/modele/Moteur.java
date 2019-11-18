package doc.num.projet.modele;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * moteur
 */
@Embeddable
@Data
@Entity
@Table(name = "MOTEUR")
@JacksonXmlRootElement(localName = "Moteur")
public class Moteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* rajouté !!! */
    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty
    private int puissance;

    @JacksonXmlProperty
    private int nombre;

    public Moteur() {

    }

    public Moteur(String type, int puissance, int nombre) {
        this.type = type;
        this.puissance = puissance;
        this.nombre = nombre;
    }

}
