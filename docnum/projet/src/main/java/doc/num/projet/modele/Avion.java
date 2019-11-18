package doc.num.projet.modele;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

/**
 * Avions
 */
@Entity
@Data
@JacksonXmlRootElement(localName = "Avion")
public class Avion implements Serializable {

    /**
     *
     */
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
    
    @OneToOne
    private Moteur moteur;

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
