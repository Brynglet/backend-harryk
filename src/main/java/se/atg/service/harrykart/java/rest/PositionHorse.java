package se.atg.service.harrykart.java.rest;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

@Builder
public class PositionHorse {

    private int position;

    private String horse;

    @JsonIgnore
    /* For testing purposes */
    private Double totalTime;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getHorse() {
        return horse;
    }

    public void setHorse(String horse) {
        this.horse = horse;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTime = totalTime;
    }
}
