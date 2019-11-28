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

         String nomNoeudPrincipal = doc.getDocumentElement().getNodeName();
         String intituleAction = doc.getDocumentElement().getAttribute("intitule");
         String idFic = doc.getDocumentElement().getAttribute("idFic");
         String nomVerif = doc.getDocumentElement().getAttribute("nomVerif");
         String checksum = doc.getDocumentElement().getAttribute("checksum");
         String dateAction = doc.getDocumentElement().getAttribute("dateAction");

         System.out.println("Root element :" + nomNoeudPrincipal);
         System.out.println("Intitule :" + intituleAction);
         System.out.println("idFic :" + idFic);
         System.out.println("nomVerif :" + nomVerif);
         System.out.println("checksum :" + checksum);
         System.out.println("dateAction :" + dateAction);

         /* inscription des infos dans le log */
         Scribe.logMemoire("Intitule de l'action          : " + intituleAction + "\n" +
                           "Identifiant unique du fichier : " + idFic + "\n" +
                           "Nom du vérificateur           : " + nomVerif + "\n" +
                           "Checksum déclaré du fichier   : " + checksum + "\n" +
                           "Date de l'action déclarée     : " + dateAction + "\n\n"
                           );


         // Vérifier si la date de l'info est valide  
         if(!checker.isDateValid(fileName, dateAction)) {
             return;
         }         
         
         NodeList nList = doc.getElementsByTagName("avion");
         
         /* on récupère le nb d'avion pour le comparer au checksum */
         int nbAvion = nList.getLength();
         String verdictChecksum = "bonChecksum";
         if(nbAvion != Integer.parseInt(checksum)){
            verdictChecksum = "faux";
            checker.isChecksumCorrect("'checksum'" + " dans le fichier " + fileName, verdictChecksum);
            return;
         }
      
         System.out.println("!!!!!!!!!!!!!!nombre d'avions = " + nbAvion + "\n");
         System.out.println("!!!!!!!!!!!!!!!!!!verdictChecksum = " + verdictChecksum);

         System.out.println("----------------------------");

         System.out.println("Root element :" + intituleAction);

         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;

               String avionId = eElement.getAttribute("id");
               String avionName = eElement.getElementsByTagName("name").item(0).getTextContent();
               String avionWeight = eElement.getElementsByTagName("weight").item(0).getTextContent();
               String moteurType = eElement.getElementsByTagName("moteur").item(0).getAttributes().item(0).getTextContent();
               String moteurPuissance = eElement.getElementsByTagName("puissance").item(0).getTextContent();
               String moteurNombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();

               System.out.println("Avion Id : " + avionId);
               System.out.println("Name : " + avionName);
               System.out.println("Weight : " + avionWeight);

               /* partie moteur */
               System.out.println("Moteur type: " + moteurType);
               System.out.println("Moteur puissance: " + moteurPuissance);
               System.out.println("Moteur nombre: " + moteurNombre);

               // Create the Avion object
               Avion avion = new Avion(
                  Long.parseLong(avionId),
                  avionName, 
                  Integer.parseInt(avionWeight),
                  new Moteur(moteurType,Integer.parseInt(moteurPuissance), Integer.parseInt(moteurNombre))
                  );

               /* on ajoute un nouvel avion à la base de données */

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
                   // Véfiriction unitaire des infos
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

         // Création de l'objet Memoire et stockage en BDD

         SimpleDateFormat dateFormattee=new SimpleDateFormat("yyyy-MM-dd");
         Date date = dateFormattee.parse(doc.getDocumentElement().getAttribute("dateAction"));

         Memoire memoire = new Memoire(doc.getDocumentElement().getAttribute("intitule"),
         doc.getDocumentElement().getAttribute("idFic"),
         doc.getDocumentElement().getAttribute("nomVerif"),
         Integer.parseInt(doc.getDocumentElement().getAttribute("checksum")),
         date
         );

         restClient.addNewMemoire(memoire);

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
