package se.atg.service.harrykart.java.rest;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;
import se.atg.service.harrykart.java.generated.HarryKartType;
import se.atg.service.harrykart.java.generated.ParticipantType;

import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HarryKartServiceImpl implements HarryKartService {

    private static final int LAP_LENGTH = 1000;
    private static final int MEDAL_FINISHERS = 3;

    @Override
    public HarryResp getInfo(String inputStr) {

        JAXBElement<HarryKartType> jtransformed = transform(inputStr);

        HarryKartType hkt = jtransformed.getValue();

        List<HorseDTO> horseDTOs = hkt.getStartList().getParticipant().stream()
                .map(x -> getHorseDto(x, hkt))
                .sorted((x1, x2) -> x1.getTotalTime() > x2.getTotalTime() ? 1 : -1)
                .limit(MEDAL_FINISHERS)
                .collect(Collectors.toList());

        return convertToResponse(horseDTOs);
    }

    private HarryResp convertToResponse(List<HorseDTO> horseDTOs) {

        List<PositionHorse> positionHorses = new ArrayList<>();

        for (int k = 0; k < horseDTOs.size(); k++) {
            var positionHorse = new PositionHorse();
            positionHorse.setHorse(horseDTOs.get(k).getHorseName());
            positionHorse.setPosition(k + 1);

            positionHorses.add(positionHorse);
        }

        HarryResp harryResp = new HarryResp();
        harryResp.setRanking(positionHorses);

        return  harryResp;
    }

    private HorseDTO getHorseDto(ParticipantType x, HarryKartType hkt) {

        HorseDTO horseDTO = new HorseDTO();

        horseDTO.setHorseName(x.getName());
        horseDTO.setLaneNr(x.getLane());
        Double totalTime = getTotalTime(x, hkt);
        horseDTO.setTotalTime(totalTime);

        return horseDTO;
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
    private JAXBElement<HarryKartType> transform(String xmlString) {

        JAXBElement<HarryKartType> jhkt = null;
        try {
            JAXBContext jc = JAXBContext.newInstance("se.atg.service.harrykart.java.generated");
            Unmarshaller um = jc.createUnmarshaller();
            jhkt = (JAXBElement<HarryKartType>) um.unmarshal(new StringReader(xmlString));
            int k = 1;

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return jhkt;
    }
}
