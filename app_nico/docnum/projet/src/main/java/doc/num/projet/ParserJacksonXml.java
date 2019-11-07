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
            String readContent = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\XML\\flottille.xml")));

            // deserialize from the XML into a Phone object
            Flottille deserializedData = xmlMapper.readValue(readContent, Flottille.class);

            // Print object details
            System.out.println("Détail de la flottille d'avions: \n");
            
            for(int i=0; i < deserializedData.getAvion().size(); i++){
                System.out.println("\tId: " + deserializedData.getAvion().get(i).getId());
                System.out.println("\tName: " + deserializedData.getAvion().get(i).getName());
                System.out.println("\tWeight: " + deserializedData.getAvion().get(i).getWeight());
                System.out.println();
            }
            
        } catch (IOException e) {
            // handle the exception
        }
    }

}