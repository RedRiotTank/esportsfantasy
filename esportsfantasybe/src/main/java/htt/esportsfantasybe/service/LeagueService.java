package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.LeagueDTO;
import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.Player;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.User;
import htt.esportsfantasybe.model.complexentities.BidUp;
import htt.esportsfantasybe.model.complexentities.Event;
import htt.esportsfantasybe.model.complexentities.Market;
import htt.esportsfantasybe.model.complexentities.UserXLeague;
import htt.esportsfantasybe.model.pojos.*;
import htt.esportsfantasybe.repository.LeagueRepository;
import htt.esportsfantasybe.service.complexservices.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class LeagueService {
    private int INITIAL_MONEY = 8000000;

    @Autowired
    private LeagueRepository leagueRepository;

    private UserService userService;
    private UserXLeagueService userXLeagueService;
    private UserXLeagueXPlayerService userXLeagueXPlayerService;

    private RealLeagueService realLeagueService;

    private MarketService marketService;
    private PlayerService playerService;

    private EventService eventService;
    private BidUpService bidUpService;
    private TransferPostService transferPostService;

    // invitation codes:
    private HashMap<String, UUID> invitationCodes = new HashMap<>();
    private HashMap<String, String> invitationCodesReverse = new HashMap<>();
    private final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int CODE_SIZE = 6;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @Autowired
    public LeagueService(
            UserService userService,
            UserXLeagueService userXLeagueService,
            RealLeagueService realLeagueService,
            MarketService marketService,
            PlayerService playerService,
            EventService eventService,
            UserXLeagueXPlayerService userXLeagueXPlayerService,
            BidUpService bidUpService,
            TransferPostService transferPostService) {
        this.userService = userService;
        this.userXLeagueService = userXLeagueService;
        this.realLeagueService = realLeagueService;
        this.marketService = marketService;
        this.playerService = playerService;
        this.eventService = eventService;
        this.userXLeagueXPlayerService = userXLeagueXPlayerService;
        this.bidUpService = bidUpService;
        this.transferPostService = transferPostService;
    }

    public byte[] getLeagueIcon(String uuid){
        League league = this.leagueRepository.findById(UUID.fromString(uuid)).orElseThrow(RuntimeException::new);

        return realLeagueService.getRLeagueIcon(league.getRealLeague().getUuid().toString());
    }

    public List<UserLeagueInfoPOJO> getUserLeagues(String mail){

        UserDTO userDTO = userService.getUser(mail);

        List<UserXLeague> userLeagues = this.userXLeagueService.getUserXLeaguesForUser(userDTO.getUuid());

        List<UserLeagueInfoPOJO> userLeagueInfoPOJOS = new ArrayList<>();

        userLeagues.forEach(userXLeague -> {
            League league = this.leagueRepository.findById(userXLeague.getId().getLeagueuuid()).orElseThrow(RuntimeException::new);

            if(league != null) userLeagueInfoPOJOS.add(
                    new UserLeagueInfoPOJO(
                            league.getUuid().toString(),
                            league.getName(),
                            userXLeague.isAdmin(),
                            userXLeague.getMoney(),
                            league.getRealLeague().getUuid().toString(),
                            league.getRealLeague().getGame(),
                            league.isActiveclause(),
                            invitationCodesReverse.get(league.getUuid().toString())
            ));
        });

        return userLeagueInfoPOJOS;
    }

    public List<UserLeagueInfoPOJO> getUserLeagues(UUID usruuid){
        UserDTO user = userService.getUser(usruuid);

        return getUserLeagues(user.getMail());
    }

    public List<League> getAllLeagues(){
        return this.leagueRepository.findAll();
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // 24h
    public void updateInvCodes(){
        Utils.esfPrint("Updating invitation codes");

        invitationCodes.clear();

        getAllLeagues().forEach(league -> {
            generateInvitationCode(league.getUuid());
        });

        Utils.esfPrint("Invitation codes updated");
    }

    public List<LeagueInvCodePOJO> getInvCodes(UUID useruuid){
        List<LeagueInvCodePOJO> leagueInvCodePOJOS = new ArrayList<>();

        getUserLeagues(useruuid).forEach(userLeagueInfoPOJO -> {
            String uuid = userLeagueInfoPOJO.getLeagueUUID();
            String code = invitationCodesReverse.get(uuid);

            leagueInvCodePOJOS.add(new LeagueInvCodePOJO(UUID.fromString(uuid), code));

        });

        return leagueInvCodePOJOS;
    }

    public String generateInvitationCode(UUID leagueUUID) {
        String code;
        do {
            code = generateCode();
        } while (invitationCodes.containsKey(code));

        invitationCodes.put(code, leagueUUID);
        invitationCodesReverse.put(leagueUUID.toString(), code);
        return code;
    }

    private String generateCode() {
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < CODE_SIZE; i++)
            codeBuilder.append(CHARS.charAt((int) (Math.random() * CHARS.length())));

        return codeBuilder.toString();
    }

    public void joinLeague(JoinLeaguePOJO joinLeaguePOJO) {

        UserDTO userDTO = userService.getUser(joinLeaguePOJO.getUserMail());

        switch (joinLeaguePOJO.getLeagueType()) {
            case 1:

                RealLeague rl = realLeagueService.getRLeague(joinLeaguePOJO.getCompetition());

                League league = leagueRepository.save(new League(joinLeaguePOJO.getLeagueName(), joinLeaguePOJO.isClauseActive(), joinLeaguePOJO.getStartType(), rl, joinLeaguePOJO.isPublicLeague()));

                userXLeagueService.linkUserToLeague(userDTO.getUuid(), league.getUuid(), true, INITIAL_MONEY);

                marketService.initMarket(new LeagueDTO(league));

                if(joinLeaguePOJO.getStartType() == 1){
                    int resultmoney = randomPlayerLinking(league, userDTO);
                    userXLeagueService.setMoney(userDTO.getUuid(), league.getUuid(), resultmoney);
                }

                generateInvitationCode(league.getUuid());

                break;

            case 2:
                Set<League> leagues = getPublicLeaguesWithTournament(joinLeaguePOJO.getCompetition());

                League randomLeague;
                int trycount = 0;

                do {
                    int randomLeagueIndex = (int) (Math.random() * leagues.size());
                    randomLeague = leagues.stream().toList().get(randomLeagueIndex);
                    trycount++;
                } while (userXLeagueService.isUserInLeague(userDTO.getUuid(), randomLeague.getUuid()) && trycount < 5);

                if (trycount == 5) throw new RuntimeException("1011");

                userXLeagueService.linkUserToLeague(userDTO.getUuid(), randomLeague.getUuid(), false, INITIAL_MONEY);

                if(randomLeague.getStartingtype() == 1){
                    int resultmoney = randomPlayerLinking(randomLeague, userDTO);
                    userXLeagueService.setMoney(userDTO.getUuid(), randomLeague.getUuid(), resultmoney);
                }
                break;

            case 3:

                UUID leagueuuid = this.invitationCodes.get(joinLeaguePOJO.getCode());

                if (leagueuuid == null) throw new RuntimeException("1012");

                League leagueUnionByCode = leagueRepository.findById(leagueuuid).orElse(null);

                if (leagueUnionByCode == null) throw new RuntimeException("1013");

                if (userXLeagueService.isUserInLeague(userDTO.getUuid(), leagueUnionByCode.getUuid()))
                    throw new RuntimeException("1014");

                userXLeagueService.linkUserToLeague(userDTO.getUuid(), leagueUnionByCode.getUuid(), false, INITIAL_MONEY);

                if(leagueUnionByCode.getStartingtype() == 1){
                    int resultmoney = randomPlayerLinking(leagueUnionByCode, userDTO);
                    userXLeagueService.setMoney(userDTO.getUuid(), leagueUnionByCode.getUuid(), resultmoney);
                }

                break;
            default:
                throw new RuntimeException("1000");
        }


    }

    public Set<League> getPublicLeaguesWithTournament(String tournamentID){
        RealLeague rl = realLeagueService.getRLeague(tournamentID);

        if(rl == null) throw new RuntimeException("1009");

        Set<League> leagues = this.leagueRepository.findAllByRealLeagueAndPublicleague(rl, true);

        if (leagues == null || leagues.isEmpty()) throw new RuntimeException("1010");

        return leagues;
    }

    public void setInvCodes(HashMap<String, UUID> invitationCodes) {
        this.invitationCodes = invitationCodes;
    }

    //@Scheduled(fixedRate = 12 * 60 * 60 * 1000) // 12h for normal use
    @Scheduled(fixedRate =  30 * 1000) // 30s for testing
    public void updateAllMarkets(){
        Utils.esfPrint("Updating all markets");

        List<League> leagues = this.leagueRepository.findAll();

        leagues.forEach(league -> {
            marketService.updateMarket(new LeagueDTO(league));
        });

        Utils.esfPrint("All markets updated");
    }


    public Set<PlayerInfoPOJO> getMarketPlayersInfo(UUID leagueUUID, UUID userUUID) {

        List<Market> playersInSell = this.marketService.getLeagueMarketEntriesInSell(leagueUUID,true);
        Set<PlayerInfoPOJO> playerInfoPOJOS = new HashSet<>();


        playersInSell.forEach(marketEntry -> {
            PlayerDTO playerDTO = playerService.getPlayerDTO(marketEntry.getId().getPlayeruuid());
            String ownerUsername = "Free Agent";

            if(marketEntry.getOwneruuid() != null) ownerUsername = userService.getUser(marketEntry.getOwneruuid()).getUsername();

            int bidupValue = -999;

            if(userUUID != null) {
                BidUp bidUp = bidUpService.getBidUp(leagueUUID, playerDTO.getUuid(), userUUID);
                if (bidUp != null)bidupValue = bidUp.getBid();
            }

            playerInfoPOJOS.add(
                    new PlayerInfoPOJO(
                            playerDTO.getUuid(),
                            playerDTO.getUsername(),
                            playerDTO.getRole(),
                            playerDTO.getTeams(),
                            marketEntry.getOwneruuid(),
                            ownerUsername,
                            marketEntry.getMarketvalue(),
                            bidupValue,
                            11,
                            250,
                            eventService.getPlayerPointsHistory(playerDTO.getUuid())
                    )
            );
        });

        return playerInfoPOJOS;
    }

    public CurrentJourInfoPOJO getLeagueRealLeagueCurrentJour(UUID leagueuuid){
        return this.realLeagueService.getRLeagueCurrentJour(this.getLeague(leagueuuid).getRealLeague().getUuid().toString());
    }

    public League getLeague(UUID leagueuuid){
        return this.leagueRepository.findById(leagueuuid).orElseThrow(RuntimeException::new);
    }

    public RankingPOJO getRankings(RankPOJO rankPOJO){

        League league = this.getLeague(rankPOJO.getLeagueUuid());

        ArrayList<UserRank> jourRank = new ArrayList<>();
        ArrayList<UserRank> totalRank = new ArrayList<>();

        Set<Event> evs = eventService.getEvents(league.getRealLeague().getUuid());

        league.getUsers().forEach(user -> {
            String b64Icon = "null";
            try {
                b64Icon = Base64.getEncoder().encodeToString(userService.getUserPfp(user.getUuid()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            UserRank userJourRank = new UserRank(user.getUuid(), user.getUsername(), b64Icon);
            UserRank userTotalRank = new UserRank(user.getUuid(), user.getUsername(), b64Icon);

            //JourPoints
            int jourpoints = userXLeagueXPlayerService.getJourLeagueUserPoints(user, league, rankPOJO.getJour());
            userJourRank.setPoints(jourpoints);

            //TotalPoints
            Set<Integer> contemplatedJours = new HashSet<>();

            evs.forEach(ev -> {
                contemplatedJours.add(ev.getId().getJour());
            });

            contemplatedJours.forEach(jour -> {
                int points = userXLeagueXPlayerService.getJourLeagueUserPoints(user, league, jour);
                userTotalRank.addPoints(points);
            });

            insertOrdered(jourRank, userJourRank);
            insertOrdered(totalRank, userTotalRank);

        });

       return new RankingPOJO(jourRank, totalRank);
    }

    private static void insertOrdered(List<UserRank> list, UserRank newUserRank) {
        int index = 0;
        while (index < list.size() && list.get(index).compareTo(newUserRank) < 0) {
            index++;
        }
        list.add(index, newUserRank);
    }

    public Set<PlayerInfoPOJO> getAllPlayers(String uuid) {
        Set<PlayerInfoPOJO> allPlayers = new HashSet<>();

        League league = getLeague(UUID.fromString(uuid));

        RealLeague rl = realLeagueService.getRLeague(league.getRealLeague().getUuid().toString());

        rl.getTeams().forEach(team -> {
            allPlayers.addAll(realLeagueService.getTeamPlayersInfo(team.getUuid().toString(), uuid));
        });

        return allPlayers;
    }

    public void leaveLeague(UUID leagueuuid, UUID useruuid) {
        userXLeagueXPlayerService.unlinkAllUserLeaguePlayer(useruuid, leagueuuid);
        marketService.deleteAllplayersOwner(useruuid, leagueuuid);
        userXLeagueService.unlinkUserFromLeague(useruuid, leagueuuid);

        League league = this.leagueRepository.findById(leagueuuid).orElseThrow(RuntimeException::new);

        if(league.getUsers().isEmpty()) deleteLeague(leagueuuid);
    }

    public void deleteLeague(UUID leagueuuid) {
        League league = this.leagueRepository.findById(leagueuuid).orElseThrow(RuntimeException::new);

        bidUpService.deleteLeagueBidups(leagueuuid);
        marketService.deleteLeagueMarketEntries(leagueuuid);
        transferPostService.deleteLeagueTransferPost(leagueuuid);

        this.leagueRepository.delete(league);
    }

    private int randomPlayerLinking(League league, UserDTO userDTO){
        int totalAmount = 0;
        int trys = 0;
        List<Player> playerList = new ArrayList<>();

        List<Market> marketsNoOwner = marketService.getLeagueMarketEntriesWithNoOwner(league.getUuid());

        while(totalAmount < INITIAL_MONEY && trys < 100 && playerList.size() < 5){
            int randomIndex = (int) (Math.random() * marketsNoOwner.size());
            Market market = marketsNoOwner.get(randomIndex);
            Player player = playerService.getPlayer(market.getId().getPlayeruuid());

            if(!playerList.contains(player)){
                playerList.add(player);
                userXLeagueXPlayerService.linkUserLeaguePlayer(userDTO.getUuid(), league.getUuid(), player.getUuid(), league.getRealLeague().getCurrentjour());
                marketService.stablishMarketProperty(market, userDTO.getUuid());
                totalAmount += player.getValue();
            }
            trys++;
        }

        return INITIAL_MONEY - totalAmount;
    }
}
