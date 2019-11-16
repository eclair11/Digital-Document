package doc.num.projet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.Data;

/**
 * DeserializeXML
 */
@Data
public class ParserJacksonXml {

    public static void Deserialize() {
        
        try {
            XmlMapper xmlMapper = new XmlMapper();

            // read file and put contents into the string
            String readContent = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\XML\\action2.xml"))); /* ajout */
            //String readContent = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\XML\\action3.xml"))); /* mise à jour */
            //String readContent = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\XML\\action.xml"))); /* suppression */

            // deserialize from the XML into a Action object
            Action deserializedData = xmlMapper.readValue(readContent, Action.class); // Action.class à écrire

            // Print object details
            System.out.println("Détail de l'action en cours: \n");
            System.out.println("\tIntitulé: " + deserializedData.getIntitule());
            System.out.println();

            for(int i=0; i < deserializedData.getAvion().size(); i++){
                System.out.println("\tId: " + deserializedData.getAvion().get(i).getId());
                System.out.println("\tName: " + deserializedData.getAvion().get(i).getName());
                System.out.println("\tWeight: " + deserializedData.getAvion().get(i).getWeight());

                System.out.println("\tType moteur: " + deserializedData.getAvion().get(i).getMoteur().getType());
                System.out.println("\tPuissance moteur: " + deserializedData.getAvion().get(i).getMoteur().getPuissance());
                System.out.println("\tNombre moteur: " + deserializedData.getAvion().get(i).getMoteur().getNombre());

                System.out.println();

                /**
                 * Sauvegarde à implanter ici
                 * il faudrait commencer à vérifier les cas des intitulés
                 * add, update, delete
                 */

            }
            
        } catch (IOException e) {
            // handle the exception
        }
    }

}