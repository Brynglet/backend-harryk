package se.atg.service.harrykart.java.rest.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PositionHorse {

    private int position;

    private String horse;

    //@JsonIgnore
    /* For testing purposes */
    private Double totalTime;
}
