package htt.esportsfantasybe.model.pojos;

import lombok.Data;

import java.util.List;

@Data
public class CurrentJourInfoPOJO {
    int jour;
    boolean started;
    List<Integer> jourlist;

    public CurrentJourInfoPOJO(int jour, boolean started, List<Integer> jourlist) {
        this.jour = jour;
        this.started = started;
        this.jourlist = jourlist;
    }
}
