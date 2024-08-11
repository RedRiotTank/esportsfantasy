package htt.esportsfantasybe.model.pojos;

import lombok.Data;

@Data
public class UserReducedBidInfoPOJO {
    private String useruuid;
    private String username;
    private String usericon;
    private int bid;

    public UserReducedBidInfoPOJO(String useruuid, String username, String usericon, int bid) {
        this.useruuid = useruuid;
        this.username = username;
        this.usericon = usericon;
        this.bid = bid;
    }
}
