package doc.num.projet;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import lombok.Data;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

@Data
public class ParserDomJava {

    
    public ParserDomJava() {
        
    }

    public static void parseXMLDomJava(AvionRepository avionRep) {
      try {
         //Spécifier url
         File inputFile = new File("src/main/resources/XML/action2.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         System.out.println("Intitule :" + doc.getDocumentElement().getAttribute("intitule"));
         NodeList nList = doc.getElementsByTagName("avion");
         System.out.println("----------------------------");

          System.out.println("Root element :" + doc.getDocumentElement().getAttribute("intitule"));

         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               System.out.println("Avion Id : " + eElement.getAttribute("id"));
               System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
               System.out.println("Weight : " + eElement.getElementsByTagName("weight").item(0).getTextContent());
               
               /* partie moteur */
               System.out.println("Moteur type: " + eElement.getElementsByTagName("moteur").item(0).getAttributes().item(0).getTextContent());
               System.out.println("Moteur puissance: " + eElement.getElementsByTagName("puissance").item(0).getTextContent());
               System.out.println("Moteur nombre: " + eElement.getElementsByTagName("nombre").item(0).getTextContent());

               // ENREGISTRER LES AVIONS DANS LA DB
               // Create the Avion object
               Avion avion = new Avion(Long.parseLong(eElement.getAttribute("id")), 
                     eElement.getElementsByTagName("name").item(0).getTextContent(), 
                     Integer.parseInt(eElement.getElementsByTagName("weight").item(0).getTextContent()),
                     new Moteur(
                        eElement.getElementsByTagName("moteur").item(0).getAttributes().item(0).getTextContent(),
                        Integer.parseInt(eElement.getElementsByTagName("puissance").item(0).getTextContent()),
                        Integer.parseInt(eElement.getElementsByTagName("nombre").item(0).getTextContent())
                     ));
               // Save or update it in the DB
               System.out.println(avionRep);
               System.out.println(avion);
               avionRep.save(avion);
               
            }
         }
       
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   
}
