package doc.num.projet.modele;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * 
 * @author Nicolas T.
 * 
 * Classe des logs
 */
@Data
@Entity
@Table(name = "MEMOIRE")
public class Memoire {

    /* VARIABLES */

    /* Identifiant unique de Memoire */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* Attributs de la balise <action>*/

    /* type d'actione effectuée */
    private String intitule; /* lui n'a normalement pas à être sauvegardé */

    /**
     * id unique du fichier
     * un fichier ne peut être employé plus d'une fois
     */
    private String idFic;

    /**
     * id unique du fichier
     * un fichier ne peut être employé plus d'une fois
     */
    private String nomVerif;

    /**
     * nombre d'informations déclarées par action 
     * qui doit correspondre avec le nb réel 
     */
    private int checksum;

    @Temporal(TemporalType.DATE)
    private Date dateAction;
    
    /* CONSTRUCTEURS */

    public Memoire() {
    }

	public Memoire(String intitule, String idFic, String nomVerif, int checksum, Date dateAction) {
		this.intitule = intitule;
		this.idFic = idFic;
		this.nomVerif = nomVerif;
		this.checksum = checksum;
		this.dateAction = dateAction;
	}

    
    
}