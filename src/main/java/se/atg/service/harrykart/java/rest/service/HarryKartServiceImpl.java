package se.atg.service.harrykart.java.rest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.atg.service.harrykart.java.generated.HarryKartType;
import se.atg.service.harrykart.java.generated.LaneType;
import se.atg.service.harrykart.java.generated.ParticipantType;
import se.atg.service.harrykart.java.rest.pojo.HarryResponse;
import se.atg.service.harrykart.java.rest.pojo.HorseDTO;
import se.atg.service.harrykart.java.rest.pojo.PositionHorse;
import se.atg.service.harrykart.java.rest.utility.XmlConverter;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static se.atg.service.harrykart.java.rest.utility.CommonConstants.*;

@Slf4j
@Service
public class HarryKartServiceImpl implements HarryKartService {

    @Override
    public HarryResponse getResponse(String xmlStr) {

        XmlConverter xmlConverter = new XmlConverter();

        var harryKartType = xmlConverter.transformXmlToJava(xmlStr);

        List<HorseDTO> horseDTOs = harryKartType.getStartList().getParticipant().stream()
                .map(x -> getHorseDto(x, harryKartType))
                .sorted((x1, x2) -> x1.getTotalTime() > x2.getTotalTime() ? 1 : -1)
                .limit(NR_OF_MEDAL_FINISHERS)
                .collect(Collectors.toList());

        return convertToResponse(horseDTOs);
    }

    private HarryResponse convertToResponse(List<HorseDTO> horseDTOs) {

        AtomicInteger pos = new AtomicInteger();

        List<PositionHorse> responseInfo = horseDTOs.stream()
                .sorted((x1, x2) -> x1.getTotalTime() > x2.getTotalTime() ? 1 : -1)
                .map(x -> PositionHorse.builder()
                            .horse(x.getHorseName())
                            .position(pos.incrementAndGet())
                            .totalTime(x.getTotalTime())
                            .build())
                .collect(Collectors.toList());

        return HarryResponse.builder()
                .ranking(responseInfo)
                .build();
    }

    private HorseDTO getHorseDto(ParticipantType x, HarryKartType hkt) {

        Double totalTime = getTotalRaceTime(x, hkt);

        return HorseDTO.builder()
                .horseName(x.getName())
                .laneNr(x.getLane())
                .totalTime(totalTime)
                .build();
    }

    private Double getTotalRaceTime(ParticipantType participantType, HarryKartType hkt) {

        int laneNr = participantType.getLane().intValue();

        List<BigInteger> bumpUps = hkt.getPowerUps().getLoop().stream()
                .sorted((x1, x2) -> x1.getNumber().intValue() > x2.getNumber().intValue() ? 1 : -1)
                .flatMap(x -> x.getLane().stream())
                .filter(x -> laneNr == x.getNumber().intValue())
                .map(LaneType::getValue)
                .collect(Collectors.toList());

        double lapSpeed = getLapSpeed(participantType.getBaseSpeed().doubleValue(), DOUBLE_ZERO);
        double totalTime = LAP_LENGTH / lapSpeed;

        for (BigInteger bumpUp : bumpUps) {
            lapSpeed = getLapSpeed(lapSpeed, bumpUp.doubleValue());
            totalTime += LAP_LENGTH / lapSpeed;
        }

        return totalTime;
    }

    private Double getLapSpeed(Double currentSpeed, Double extraSpeed) {

        double lapSpeed = currentSpeed + extraSpeed;

        if (lapSpeed <= 0) {
            log.error(ZonedDateTime.now() + ". getLapSpeed exception lapSpeed <= 0 :" + lapSpeed);
            throw new RuntimeException("getLapSpeed exception lapSpeed <= 0 :" + lapSpeed);
        }

        return lapSpeed;
    }
}
