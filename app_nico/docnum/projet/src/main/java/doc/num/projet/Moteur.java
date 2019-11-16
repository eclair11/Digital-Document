package doc.num.projet;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import javax.persistence.Table;

import lombok.Data;

/**
 * moteur
 */
@Embeddable
@Data
@Table(name = "MOTEUR")
@JacksonXmlRootElement(localName = "Moteur")
public class Moteur {

    

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