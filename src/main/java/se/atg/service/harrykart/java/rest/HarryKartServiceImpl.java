package se.atg.service.harrykart.java.rest;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;
import se.atg.service.harrykart.java.generated.HarryKartType;

import java.io.StringReader;

@Service
public class HarryKartServiceImpl implements  HarryKartService{

    @Override
    public String getInfo(String inputStr) {

        JAXBElement<HarryKartType> transformed = transform(inputStr);
        return "ok!";
    }

    @SuppressWarnings("unchecked")
    private JAXBElement<HarryKartType> transform(String xmlString) {

        JAXBElement<HarryKartType> hkt = null;
        try {
            JAXBContext jc = JAXBContext.newInstance("se.atg.service.harrykart.java.generated");
            Unmarshaller um = jc.createUnmarshaller();
            hkt = (JAXBElement<HarryKartType>) um.unmarshal(new StringReader(xmlString));
            int k = 1;

        } catch (JAXBException e) {
            e.printStackTrace();
        }


        return hkt;
    }
}
