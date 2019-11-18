/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doc.num.projet.service;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

/**
 *
 * @author Elias R.
 */
@Service
public class FileChecker {
    
    public boolean checkFile(String xmlFileName) {
        String xsdPath = "./src/main/resources/XSD/validator.xsd";
        //String xmlPath = "./src/main/resources/XML/" + xmlFileName;
        String xmlPath = "data/XML/" + xmlFileName;
        return this.validateXmlFile(xsdPath, xmlPath);
        
    }
    
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
    
}
