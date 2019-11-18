/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doc.num.projet.service;

import org.springframework.stereotype.Service;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import lombok.Data;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.inject.Inject;
import doc.num.projet.modele.*;

/**
 *
 * @author Nicolas T.
 */
@Service
public class FileParser {
    
    @Inject RestClient restClient;
    
     public void parse(String fileName) {
      try {
         //File inputFile = new File("src/main/resources/XML/" + fileName);
         File inputFile = new File("data/XML/" + fileName);
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

               // Create the Avion object
               Avion avion = new Avion(Long.parseLong(eElement.getAttribute("id")), 
                     eElement.getElementsByTagName("name").item(0).getTextContent(), 
                     Integer.parseInt(eElement.getElementsByTagName("weight").item(0).getTextContent()), 1,
                     new Moteur(
                        eElement.getElementsByTagName("moteur").item(0).getAttributes().item(0).getTextContent(),
                        Integer.parseInt(eElement.getElementsByTagName("puissance").item(0).getTextContent()),
                        Integer.parseInt(eElement.getElementsByTagName("nombre").item(0).getTextContent())
                     ));
               
               // ask the RestClient to add New avion in DB
               restClient.addNewAvion(avion);
               
            }
         }
       
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
