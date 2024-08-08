package htt.esportsfantasybe.model.pojos;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RankingPOJO {

    ArrayList<UserRank> jourRank, totalRank;

    public RankingPOJO(ArrayList<UserRank> jourRank, ArrayList<UserRank> totalRank) {
        this.jourRank = jourRank;
        this.totalRank = totalRank;
    }
}
