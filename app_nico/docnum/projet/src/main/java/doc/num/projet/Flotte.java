package doc.num.projet;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

/**
 * Flotte
 */
@Data
@JacksonXmlRootElement
public class Flotte {

    @JacksonXmlProperty(localName = "Avion")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Avion> flotte = new ArrayList<>();

}