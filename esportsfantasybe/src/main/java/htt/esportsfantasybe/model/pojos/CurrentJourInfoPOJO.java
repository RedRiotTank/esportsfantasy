package htt.esportsfantasybe.model.pojos;

import lombok.Data;

@Data
public class CurrentJourInfoPOJO {
    int jour;
    boolean started;

    public CurrentJourInfoPOJO(int jour, boolean started) {
        this.jour = jour;
        this.started = started;
    }
}
