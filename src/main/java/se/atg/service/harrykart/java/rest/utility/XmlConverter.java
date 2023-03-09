package se.atg.service.harrykart.java.rest.utility;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.atg.service.harrykart.java.generated.HarryKartType;

import java.io.Serial;
import java.io.Serializable;
import java.io.StringReader;
import java.time.ZonedDateTime;

@Slf4j
@Component
public class XmlConverter implements Serializable {

    @Serial
    private static final long serialVersionUID = -1700806627158383771L;
    public XmlConverter() {}
    @SuppressWarnings("unchecked")
    public HarryKartType transformXmlToJava(String xmlString) {

        try {

            JAXBContext jc = JAXBContext.newInstance("se.atg.service.harrykart.java.generated");
            Unmarshaller um = jc.createUnmarshaller();

            JAXBElement<HarryKartType> jaxb = (JAXBElement<HarryKartType>) um.unmarshal(new StringReader(xmlString));

            return jaxb.getValue();

        } catch (JAXBException e) {
            log.error(ZonedDateTime.now() + ". transformXmlToJAXBElement Error with xmlString:" + xmlString);
            throw new RuntimeException("transformXmlToJAXBElement JAXBException: " + e.getMessage());
        }
    }
}
