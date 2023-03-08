package se.atg.service.harrykart.java.rest.pojo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class HarryResponse implements Serializable {
    private static final long serialVersionUID = -3045816128915845702L;

    private List<PositionHorse> ranking;
}
