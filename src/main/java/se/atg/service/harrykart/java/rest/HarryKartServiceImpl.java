package se.atg.service.harrykart.java.rest;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;
import se.atg.service.harrykart.java.generated.HarryKartType;

import java.io.StringReader;
import java.util.Arrays;

@Service
public class HarryKartServiceImpl implements  HarryKartService {

    @Override
    public HarryResp getInfo(String inputStr) {
        JAXBElement<HarryKartType> transformed = transform(inputStr);

        PositionHorse positionHorse1 = new PositionHorse();
        positionHorse1.setPosition(1);
        positionHorse1.setHorse("name1");

        PositionHorse positionHorse2 = new PositionHorse();
        positionHorse2.setPosition(2);
        positionHorse2.setHorse("name2");

        PositionHorse positionHorse3 = new PositionHorse();
        positionHorse3.setPosition(3);
        positionHorse3.setHorse("name3");

        HarryResp harryResp = new HarryResp();
        harryResp.setRanking(Arrays.asList(positionHorse1, positionHorse2, positionHorse3));

        return harryResp;
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
