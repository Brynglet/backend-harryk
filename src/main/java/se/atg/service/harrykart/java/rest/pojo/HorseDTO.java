package se.atg.service.harrykart.java.rest.pojo;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data

@Builder
public class HorseDTO {

    private String horseName;
    private BigInteger laneNr;
    private Double totalTime;
}
