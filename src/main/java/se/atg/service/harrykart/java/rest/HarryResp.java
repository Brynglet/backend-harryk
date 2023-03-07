package se.atg.service.harrykart.java.rest;

import java.io.Serializable;
import java.util.List;

public class HarryResp implements Serializable {
    private static final long serialVersionUID = -3045816128915845702L;

    /*
    {
   "ranking": [
      {"position": 1, "horse": "TIMETOBELUCKY"},
      {"position": 2, "horse": "HERCULES BOKO"},
      {"position": 3, "horse": "CARGO DOOR"}
   ]
}
     */
    private List<PositionHorse> ranking;

    public List<PositionHorse> getRanking() {
        return ranking;
    }

    public void setRanking(List<PositionHorse> ranking) {
        this.ranking = ranking;
    }
}
