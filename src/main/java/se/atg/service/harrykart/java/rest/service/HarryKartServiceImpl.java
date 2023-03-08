package se.atg.service.harrykart.java.rest.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;
import se.atg.service.harrykart.java.generated.HarryKartType;
import se.atg.service.harrykart.java.generated.ParticipantType;
import se.atg.service.harrykart.java.rest.pojo.HarryResp;
import se.atg.service.harrykart.java.rest.pojo.HorseDTO;
import se.atg.service.harrykart.java.rest.pojo.PositionHorse;

import java.io.StringReader;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class HarryKartServiceImpl implements HarryKartService {

    private static final int LAP_LENGTH = 1000;
    private static final int MEDAL_FINISHERS = 3;

    @Override
    public HarryResp getResponse(String xmlStr) {

        JAXBElement<HarryKartType> xmlAsJava = transformXmlToJAXBElement(xmlStr);

        var harryKartType = xmlAsJava.getValue();

        List<HorseDTO> horseDTOs = harryKartType.getStartList().getParticipant().stream()
                .map(x -> getHorseDto(x, harryKartType))
                .sorted((x1, x2) -> x1.getTotalTime() > x2.getTotalTime() ? 1 : -1)
                .limit(MEDAL_FINISHERS)
                .collect(Collectors.toList());

        return convertToResponse(horseDTOs);
    }

    private HarryResp convertToResponse(List<HorseDTO> horseDTOs) {

        AtomicInteger pos = new AtomicInteger();
        pos.set(0);

        List<PositionHorse> responseInfo = horseDTOs.stream()
                .sorted((x1, x2) -> x1.getTotalTime() > x2.getTotalTime() ? 1 : -1)
                .map(x -> PositionHorse.builder()
                            .horse(x.getHorseName())
                            .position(pos.incrementAndGet())
                            .totalTime(x.getTotalTime())
                            .build())
                .collect(Collectors.toList());

        return HarryResp.builder()
                .ranking(responseInfo)
                .build();
    }

    private HorseDTO getHorseDto(ParticipantType x, HarryKartType hkt) {

        Double totalTime = getTotalTime(x, hkt);

        return HorseDTO.builder()
                .horseName(x.getName())
                .laneNr(x.getLane())
                .totalTime(totalTime)
                .build();
    }

    private Double getTotalTime(ParticipantType participantType, HarryKartType hkt) {

        int laneNr = participantType.getLane().intValue();

        List<BigInteger> bumpUps = hkt.getPowerUps().getLoop().stream()
                .sorted((o1, o2) -> o1.getNumber().intValue() > o2.getNumber().intValue() ? 1 : -1)
                .flatMap(x -> x.getLane().stream())
                .filter(x -> laneNr == x.getNumber().intValue())
                .map(x -> x.getValue())
                .collect(Collectors.toList());

        Double lapSpeed = participantType.getBaseSpeed().doubleValue();
        Double totalTime = LAP_LENGTH / lapSpeed;

        for (int k = 0; k < bumpUps.size(); k++) {
            lapSpeed += bumpUps.get(k).doubleValue();
            totalTime += LAP_LENGTH / lapSpeed;
        }

        return totalTime;
    }

    @SuppressWarnings("unchecked")
    private JAXBElement<HarryKartType> transformXmlToJAXBElement(String xmlString) {

        try {

            JAXBContext jc = JAXBContext.newInstance("se.atg.service.harrykart.java.generated");
            Unmarshaller um = jc.createUnmarshaller();

            return (JAXBElement<HarryKartType>) um.unmarshal(new StringReader(xmlString));

        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException("");
        }
    }
}
