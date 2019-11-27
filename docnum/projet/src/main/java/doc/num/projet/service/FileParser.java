/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doc.num.projet.service;

import java.io.File;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import doc.num.projet.modele.Avion;
import doc.num.projet.modele.Moteur;

/**
 *
 * @author Nicolas T.
 */
@Service
public class FileParser {

   @Inject
   RestClient restClient;

   @Inject
   FileChecker checker;

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

               String avionName = eElement.getElementsByTagName("name").item(0).getTextContent();
               String avionWeight = eElement.getElementsByTagName("weight").item(0).getTextContent();
               String moteurType = eElement.getElementsByTagName("moteur").item(0).getAttributes().item(0).getTextContent();
               String moteurPuissance = eElement.getElementsByTagName("puissance").item(0).getTextContent();


               // Create the Avion object
               Avion avion = new Avion(Long.parseLong(eElement.getAttribute("id")),
                     eElement.getElementsByTagName("name").item(0).getTextContent(),
                     Integer.parseInt(eElement.getElementsByTagName("weight").item(0).getTextContent()), 1,
                     new Moteur(
                           eElement.getElementsByTagName("moteur").item(0).getAttributes().item(0).getTextContent(),
                           Integer.parseInt(eElement.getElementsByTagName("puissance").item(0).getTextContent()),
                           Integer.parseInt(eElement.getElementsByTagName("nombre").item(0).getTextContent())));

               // ask the RestClient to add New avion in DB

               /* on stocke la chaîne 'intitulé' de la balise 'action' */
               String intituleAction = doc.getDocumentElement().getAttribute("intitule");

               Long idAvion = Long.parseLong(eElement.getAttribute("id"));

               /* puis on la teste pour effectuer la bonne action */
               if(intituleAction.matches("add")){
                // Véfiriction unitaire des infos
                boolean isValidAddInfos =
                        checker.checkInfo("'name'" + " dans le fichier " + fileName, avionName) &&
                        checker.checkInfo("'weight'" + " dans le fichier " + fileName, avionWeight) &&
                        checker.checkInfo("'type'" + " dans le fichier " + fileName, moteurType) &&
                        checker.checkInfo("'puissance'" + " dans le fichier " + fileName, moteurPuissance);
                if(isValidAddInfos) { restClient.addNewAvion(avion, idAvion); }
               }
               else if(intituleAction.matches("update")){
                   boolean isValidUpdateInfos =
                        checker.checkInfo("'name'" + " dans le fichier " + fileName, avionName) ||
                        checker.checkInfo("'weight'" + " dans le fichier " + fileName, avionWeight) ||
                        checker.checkInfo("'type'" + " dans le fichier " + fileName, moteurType) ||
                        checker.checkInfo("'puissance'" + " dans le fichier " + fileName, moteurPuissance);
                  if (isValidUpdateInfos) { restClient.updateNewAvion(avion, idAvion); }
               }
               else if(intituleAction.matches("delete") && idAvion > 0L){
                  restClient.deleteNewAvion(avion, idAvion);
               }

             }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
