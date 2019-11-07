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

   public static void ParserXMLDomJava() {
      try {
         //Spécifier url
         File inputFile = new File("src\\main\\resources\\XML\\flottille.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("avion");
         System.out.println("----------------------------");

         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               System.out.println("Avion Id : " + eElement.getAttribute("id"));
               System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
               System.out.println("Weight : " + eElement.getElementsByTagName("weight").item(0).getTextContent());
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   
}
