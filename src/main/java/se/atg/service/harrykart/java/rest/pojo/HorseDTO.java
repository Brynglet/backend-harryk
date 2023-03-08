package se.atg.service.harrykart.java.rest.pojo;

import lombok.Builder;

import java.math.BigInteger;

@Builder
public class HorseDTO {

    private String horseName;
    private BigInteger laneNr;
    private Double totalTime;

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public BigInteger getLaneNr() {
        return laneNr;
    }

    public void setLaneNr(BigInteger laneNr) {
        this.laneNr = laneNr;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTime = totalTime;
    }
}
