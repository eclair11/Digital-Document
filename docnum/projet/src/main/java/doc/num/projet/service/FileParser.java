/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doc.num.projet.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import doc.num.projet.modele.Avion;
import doc.num.projet.modele.Memoire;
import doc.num.projet.modele.Moteur;

/**
 *
 * @author Nicolas T.
 */
@Service
public class FileParser {

   @Inject
   RestClient restClient;

   public void parse(String fileName) {
      try {
         // File inputFile = new File("src/main/resources/XML/" + fileName);
         File inputFile = new File("data/XML/" + fileName);
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         System.out.println("Intitule :" + doc.getDocumentElement().getAttribute("intitule"));
         System.out.println("idFic :" + doc.getDocumentElement().getAttribute("idFic"));
         System.out.println("checksum :" + doc.getDocumentElement().getAttribute("checksum"));
         System.out.println("dateAction :" + doc.getDocumentElement().getAttribute("dateAction"));
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
               System.out.println("Moteur type: "
                     + eElement.getElementsByTagName("moteur").item(0).getAttributes().item(0).getTextContent());
               System.out.println(
                     "Moteur puissance: " + eElement.getElementsByTagName("puissance").item(0).getTextContent());
               System.out.println("Moteur nombre: " + eElement.getElementsByTagName("nombre").item(0).getTextContent());

               // Création de l'objet Avion
               Avion avion = new Avion(Long.parseLong(eElement.getAttribute("id")),
                     eElement.getElementsByTagName("name").item(0).getTextContent(),
                     Integer.parseInt(eElement.getElementsByTagName("weight").item(0).getTextContent()), 1,
                     new Moteur(
                           eElement.getElementsByTagName("moteur").item(0).getAttributes().item(0).getTextContent(),
                           Integer.parseInt(eElement.getElementsByTagName("puissance").item(0).getTextContent()),
                           Integer.parseInt(eElement.getElementsByTagName("nombre").item(0).getTextContent())
                           )
                           );

               


              

               // ask the RestClient to add New avion in DB

               /* on stocke la chaîne 'intitulé' de la balise 'action' */
               String intituleAction = doc.getDocumentElement().getAttribute("intitule");

               Long idAvion = Long.parseLong(eElement.getAttribute("id"));



               /* puis on la teste pour effectuer la bonne action */
               if(intituleAction.matches("add")){
                  restClient.addNewAvion(avion, idAvion);
               }
               else if(intituleAction.matches("delete")){
                  restClient.deleteNewAvion(avion, idAvion);
               }
               else if(intituleAction.matches("update")){
                  restClient.updateNewAvion(avion, idAvion);
               }

             }

             

         }

         // Création de l'objet Memoire et stockage en BDD

         SimpleDateFormat dateFormattee=new SimpleDateFormat("yyyy-MM-dd");
         Date date = dateFormattee.parse(doc.getDocumentElement().getAttribute("dateAction"));

         Memoire memoire = new Memoire(doc.getDocumentElement().getAttribute("intitule"),
         doc.getDocumentElement().getAttribute("idFic"),
         Integer.parseInt(doc.getDocumentElement().getAttribute("checksum")),
         date
         );

         restClient.addNewMemoire(memoire);

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
