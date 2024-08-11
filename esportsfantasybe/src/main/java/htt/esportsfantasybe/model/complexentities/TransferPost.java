package htt.esportsfantasybe.model.complexentities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "transferpost")
public class TransferPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tpostid;

    private Date date;
    private UUID playeruuid;
    private UUID leagueuuid;
    private UUID prevowneruuid;
    private String res;

    public TransferPost(UUID playeruuid, UUID leagueuuid, UUID prevowneruuid, String res) {

        this.date = new Date();
        this.playeruuid = playeruuid;
        this.leagueuuid = leagueuuid;
        this.prevowneruuid = prevowneruuid;
        this.res = res;
    }


    public TransferPost() {

    }
}
