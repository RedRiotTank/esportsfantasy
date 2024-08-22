package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.model.Player;
import htt.esportsfantasybe.model.User;
import htt.esportsfantasybe.model.complexentities.BidUp;
import htt.esportsfantasybe.model.complexentities.TransferPost;
import htt.esportsfantasybe.model.pojos.TransferPostPOJO;
import htt.esportsfantasybe.model.pojos.UserReducedBidInfoPOJO;
import htt.esportsfantasybe.repository.complexrepositories.TransferPostRepository;
import htt.esportsfantasybe.service.PlayerService;
import htt.esportsfantasybe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class TransferPostService {

    @Autowired
    private TransferPostRepository transferPostRepository;

    private PlayerService playerService;
    private UserService userService;

    @Autowired
    public TransferPostService(PlayerService playerService, UserService userService) {
        this.playerService = playerService;
        this.userService = userService;
    }

    public void generateTransferClausePost(UUID playeruuid, UUID leagueuuid, UUID prevowneruuid,UUID newowneruuid, int val) {
        TransferPost transferPost = new TransferPost(
                playeruuid,
                leagueuuid,
                prevowneruuid,
                "c: " + val + "," + newowneruuid.toString()
        );

        transferPostRepository.save(transferPost);
    }

    public void generateTransferPost(UUID playeruuid, UUID leagueuuid, UUID prevowneruuid, List<BidUp> bidUpList) {
        bidUpList.sort((o1, o2) -> Integer.compare(o2.getBid(), o1.getBid()));

        StringBuilder res = new StringBuilder();

        for (BidUp bidUp : bidUpList) {
            res.append(bidUp.getId().getBiduseruuid()).append(" ").append(bidUp.getBid()).append(",");
        }

        res.deleteCharAt(res.length() - 1);



        TransferPost transferPost = new TransferPost(
                playeruuid,
                leagueuuid,
                prevowneruuid,
                res.toString()
        );

        transferPostRepository.save(transferPost);
    }

    public List<TransferPostPOJO> getLeagueTransferPosts(String leagueuuid) {
        List<TransferPost> tps = transferPostRepository.findAllByLeagueuuidOrderByDateDesc(UUID.fromString(leagueuuid));

        List<TransferPostPOJO> tpPOJOs = new ArrayList<>();
        tps.forEach(tp -> {
            Player player = playerService.getPlayer(tp.getPlayeruuid());
            String pIconB64 = Base64.getEncoder().encodeToString(playerService.getPlayerIcon(player.getUuid().toString()));

            UserDTO prevOwner = null;
            String prevOwnerUser = "null";
            String prevOwnerUUID = "null";
            String prevOwnerIconB64 = "null";

            if(tp.getPrevowneruuid() != null) {
                prevOwner = userService.getUser(tp.getPrevowneruuid());
                prevOwnerUser = prevOwner.getUsername();
                prevOwnerUUID = tp.getPrevowneruuid().toString();
                try {
                    prevOwnerIconB64 = Base64.getEncoder().encodeToString(userService.getUserPfp(prevOwner.getMail()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                };
            }

            if(tp.getRes().startsWith("c:")){

                String res = tp.getRes();
                res = res.replace("c: ", "");

                String[] parts = res.split(",");

                String val = parts[0];
                String newOwnerUUID = parts[1];

                try {
                    tpPOJOs.add(new TransferPostPOJO(
                            tp.getTpostid(),
                            tp.getDate().toString(),
                            tp.getPlayeruuid().toString(),
                            player.getUsername(),
                            pIconB64,
                            tp.getLeagueuuid().toString(),
                            prevOwnerUUID,
                            prevOwnerUser,
                            prevOwnerIconB64,
                            Integer.parseInt(val),
                            newOwnerUUID,
                            userService.getUser(UUID.fromString(newOwnerUUID)).getUsername(),
                            Base64.getEncoder().encodeToString(userService.getUserPfp(userService.getUser(UUID.fromString(newOwnerUUID)).getMail()))

                    ));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                String[] splitArray = tp.getRes().split(",");

                ArrayList<String> list = new ArrayList<>(Arrays.asList(splitArray));
                ArrayList<UserReducedBidInfoPOJO> bidList = new ArrayList<>();

                list.forEach(user ->{
                    String[] splitUser = user.split(" ");
                    UserDTO userDTO = userService.getUser(UUID.fromString(splitUser[0]));

                    try {
                        bidList.add(new UserReducedBidInfoPOJO(
                                userDTO.getUuid().toString(),
                                userDTO.getUsername(),
                                Base64.getEncoder().encodeToString(userService.getUserPfp(userDTO.getMail())),
                                Integer.parseInt(splitUser[1])
                        ));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                tpPOJOs.add(new TransferPostPOJO(
                        tp.getTpostid(),
                        tp.getDate().toString(),
                        tp.getPlayeruuid().toString(),
                        player.getUsername(),
                        pIconB64,
                        tp.getLeagueuuid().toString(),
                        prevOwnerUUID,
                        prevOwnerUser,
                        prevOwnerIconB64,
                        bidList
                ));

            }




        });


        return tpPOJOs;
    }

    public void deleteLeagueTransferPost(UUID league) {
        List<TransferPost> tps = transferPostRepository.findAllByLeagueuuid(league);

        transferPostRepository.deleteAll(tps);
    }
}
