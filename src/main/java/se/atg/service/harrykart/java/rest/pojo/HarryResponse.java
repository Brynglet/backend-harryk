package se.atg.service.harrykart.java.rest.pojo;

import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public class HarryResponse implements Serializable {
    private static final long serialVersionUID = -3045816128915845702L;

    private List<PositionHorse> ranking;

    public List<PositionHorse> getRanking() {
        return ranking;
    }

    public void setRanking(List<PositionHorse> ranking) {
        this.ranking = ranking;
    }
}