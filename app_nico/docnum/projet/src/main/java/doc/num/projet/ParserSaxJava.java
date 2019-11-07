package doc.num.projet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import lombok.Data;

/**
 * ParserXML
 */
@Data
public class ParserSaxJava {

    public ParserSaxJava() {
    }

    public static void ParserXMLSaxJava() {
        try {

            SAXParserFactory spFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = spFactory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                boolean boolId = false;
                boolean boolName = false;
                boolean boolWeight = false;

                public void startElement(String uri, String localName, String qName, Attributes attributes)
                        throws SAXException {

                    System.out.println("START Element :" + qName);

                    if (qName.equalsIgnoreCase("ID")) {
                        boolId = true;
                    }

                    if (qName.equalsIgnoreCase("NAME")) {
                        boolName = true;
                    }

                    if (qName.equalsIgnoreCase("WEIGHT")) {
                        boolWeight = true;
                    }

                }

                public void endElement(String uri, String localName, String qName) throws SAXException {

                    System.out.println("END Element :" + qName);
                }

                public void characters(char c[], int start, int length) throws SAXException {

                    if (boolId) {
                        System.out.println("Id :- " + new String(c, start, length));
                        boolId = false;
                    }
                    
                    if (boolName) {
                        System.out.println("Name :- " + new String(c, start, length));
                        boolName = false;
                    }

                    

                    if (boolWeight) {
                        System.out.println("Weight :- " + new String(c, start, length));
                        boolWeight = false;
                    }
                }

            };

            saxParser.parse("src\\main\\resources\\XML\\flottille.xml", handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}