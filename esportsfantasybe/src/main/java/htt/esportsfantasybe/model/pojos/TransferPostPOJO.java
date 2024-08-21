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

    private int clause = 0;
    private String newowneruuid = null;
    private String newownername = null;
    private String newownericon = null;

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
            int clause,
            String newowneruuid,
            String newownername,
            String newownericon) {
        this.transferPostId = transferPostId;
        this.date = date;
        this.playeruuid = playeruuid;
        this.playername = playername;
        this.playericon = playericon;
        this.leagueuuid = leagueuuid;
        this.prevowneruuid = prevowneruuid;
        this.prevownername = prevownername;
        this.prevownericon = prevownericon;
        this.clause = clause;
        this.newowneruuid = newowneruuid;
        this.newownername = newownername;
        this.newownericon = newownericon;
    }
}
