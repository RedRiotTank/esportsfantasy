package htt.esportsfantasybe.model.pojos;

import htt.esportsfantasybe.model.complexentities.TransferPost;
import lombok.Data;

import java.util.List;

@Data

public class TransferPostPOJO {

    private int transferPostId;
    private String date;
    private String playeruuid;
    private String playername;
    private String playericon;
    private String leagueuuid;
    private String prevowneruuid;
    private String prevownername;
    private String prevownericon;
    private List<UserReducedBidInfoPOJO> bidList;

    public TransferPostPOJO(
            int transferPostId,
            String date,
            String playeruuid,
            String playername,
            String playericon,
            String leagueuuid,
            String prevowneruuid,
            String prevownername,
            String prevownericon,
            List<UserReducedBidInfoPOJO> bidList) {
        this.transferPostId = transferPostId;
        this.date = date;
        this.playeruuid = playeruuid;
        this.playername = playername;
        this.playericon = playericon;
        this.leagueuuid = leagueuuid;
        this.prevowneruuid = prevowneruuid;
        this.prevownername = prevownername;
        this.prevownericon = prevownericon;
        this.bidList = bidList;
    }

}
