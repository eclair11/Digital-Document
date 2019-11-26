package doc.num.projet.modele;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JacksonXmlProperty(isAttribute = true)
    private Long id;

    @JacksonXmlProperty
    private String name;

    @JacksonXmlProperty
    private int weight;

    private int nombre;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Moteur moteur;

    /*
    @Temporal(TemporalType.DATE)
    private Date dateCreation = new Date();
    */

    public Avion() {
    }

    public Avion(Long id, String name, int weight, int nombre) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.nombre = nombre;
    }

    public Avion(Long id, String name, int weight, int nombre, Moteur moteur) {
        this(id, name, weight, nombre);
        this.moteur = moteur;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
