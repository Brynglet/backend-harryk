package se.atg.service.harrykart.java.rest.utility;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.atg.service.harrykart.java.generated.HarryKartType;

import java.io.StringReader;
import java.time.ZonedDateTime;

@Slf4j
@Component
public class XmlConverter {

    @SuppressWarnings("unchecked")
    public static JAXBElement<HarryKartType> transformXmlToJAXBElement(String xmlString) {

        try {

            JAXBContext jc = JAXBContext.newInstance("se.atg.service.harrykart.java.generated");
            Unmarshaller um = jc.createUnmarshaller();

            return (JAXBElement<HarryKartType>) um.unmarshal(new StringReader(xmlString));

        } catch (JAXBException e) {
            log.error(ZonedDateTime.now() + ". transformXmlToJAXBElement Error with xmlString:" + xmlString);
            throw new RuntimeException("transformXmlToJAXBElement JAXBException: " + e.getMessage());
        }
    }
}
