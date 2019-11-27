/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doc.num.projet.service;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import doc.num.projet.modele.Message;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.Files;

/**
 *
 * @author Elias R. et Solofo R.
 *
 */
@Service
public class FileChecker {
    
    @Inject
    RestClient restClient;
    
    private final int MAX_SIZE = 5; // 5ko 
    
    /**
     * the main method that will check that the given file is valid
     * @author Elias R. et Solofo R.
     */
    public boolean checkFile(String xmlFileName) {
        String xsdPath = "./src/main/resources/XSD/validator.xsd";
        // String xmlPath = "./src/main/resources/XML/" + xmlFileName;
        String xmlPath = "data/XML/" + xmlFileName;
        
        if (!this.validateXmlFile(xsdPath, xmlPath)) {
            String contenus = "le contenus du fichier " + xmlFileName + " ne respecte pas notre XSD !";
            restClient.addNewMessage(new Message(contenus, "failure"));
        }
        
        if (!this.validateFileSize(xmlFileName)) {
            String contenus = "la taille du fichier " + xmlFileName + " dépasse" + MAX_SIZE + "ko !";
            restClient.addNewMessage(new Message(contenus, "failure"));
        }
        
        if (!this.validateFileExtension(xmlFileName)) {
            String contenus = "le fichier " + xmlFileName + " n'est pas un fichier XML !";
            restClient.addNewMessage(new Message(contenus, "failure"));
        }
        
        return 
                this.validateXmlFile(xsdPath, xmlPath) && 
                this.validateFileSize(xmlFileName) && 
                this.validateFileExtension(xmlFileName);

    }

    /**
     * check that the XML respect our XSD rules
     * @author Elias R.
     */
    private boolean validateXmlFile(String xsdPath, String xmlPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Check that the size of the file is less or equal to 5ko
     * @author Solofo R.
     */
    private boolean validateFileSize(String filename) {
        try {
            File inputFile = new File("data/XML/" + filename);
            return (inputFile.length() / 1024) <= this.MAX_SIZE;
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Check that the extension of the file is XML
     * @author Solofo R.
     */
    private boolean validateFileExtension(String filename) {
        String[] splitedFilename = filename.split("\\.");
        String extension = splitedFilename[splitedFilename.length - 1];
        return extension.equals("xml");
    }
    
    
    /**
     * code copied from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
     */
    private String readAllBytes(String filePath) {
        String content = "";
        try {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    
    /**
     * check if info is valid or not
     * @author Solofo R.
     */
    public boolean checkInfo(String titreInfo, String info) {
        if (info.length() == 0) {
            return false;
        }
        return 
                !this.isContentAscii(titreInfo, info) &&
                !this.infoContainsOneChar(titreInfo, info) && 
                !this.infoContainsMoreThan1kChar(titreInfo, info);
    }
    
    
    /**
     * Check that the content of the file is ASCII
     * @author Solofo R.
     */
    private boolean isContentAscii(String titreInfo, String info) {
        if(!Charset.forName("US-ASCII").newEncoder().canEncode(info)){
            String contenus = "Une info de type " + titreInfo + " contient un caractère non ASCII !";
            restClient.addNewMessage(new Message(contenus, "failure"));
            return true;
        }
        return false;
    }
    
    /**
     * Check that an info doesn't contains only one char
     * @author Solofo R.
     */
    private boolean infoContainsOneChar(String titreInfo, String info) {
        if (info.length() == 1) {
            String contenus = "Une info de type " + titreInfo + " ne contient q'un seul caractère !";
            restClient.addNewMessage(new Message(contenus, "failure"));
            return true;
        }
        return false;
    }
    
    /**
     * Check that an info doesn't contains more than 1000 char
     * @author Solofo R.
     */
    private boolean infoContainsMoreThan1kChar(String titreInfo, String info) {
        if(info.length() > 1000){
            String contenus = "Une info de type " + titreInfo + " contient plus de 1000 caractères !";
            restClient.addNewMessage(new Message(contenus, "failure"));
            return true;
        }
        return false;
    }
    

}
