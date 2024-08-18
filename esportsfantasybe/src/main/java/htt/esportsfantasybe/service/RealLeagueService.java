package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.pojos.CurrentJourInfoPOJO;
import htt.esportsfantasybe.model.pojos.PlayerInfoPOJO;
import htt.esportsfantasybe.repository.RealLeagueRepository;
import htt.esportsfantasybe.service.apicaller.CounterApiCaller;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import htt.esportsfantasybe.service.complexservices.EventService;
import htt.esportsfantasybe.service.complexservices.UserXLeagueXPlayerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RealLeagueService {
    @Autowired
    private RealLeagueRepository realLeagueRepository;
    private final LolApiCaller lolApiCaller = new LolApiCaller();
    private final CounterApiCaller counterApiCaller = new CounterApiCaller();

    private final TeamService teamService;

    private final EventService eventService;

    private final UserXLeagueXPlayerService userXLeagueXPlayerService;

    @Autowired
    public RealLeagueService(TeamService teamService, EventService eventService, UserXLeagueXPlayerService userXLeagueXPlayerService) {
        this.teamService = teamService;
        this.eventService = eventService;
        this.userXLeagueXPlayerService = userXLeagueXPlayerService;
    }


    // ------- UPDATE ------- //

    @Transactional
    //@Scheduled(fixedRate = 86400000) //24 hours
    public void GeneralCascadeUpdate() throws IOException {
        Utils.esfPrint("Initializing general cascade update");

        Set<RealLeagueDTO> filteredRLeagues = filterObtainedRLeagues(obtainAllRLeagues());
        Set<RealLeagueDTO> rLeaguesDB = getRLeaguesDTODB();
        removeObsoleteLeagues(filteredRLeagues, rLeaguesDB);        //TODO: CHECK CASCADE
        addNewLeaguesCascade(filteredRLeagues, rLeaguesDB);

        Utils.esfPrint("Finished general cascade update");
    }

    @Scheduled(fixedRate = 7200000)   //2 hours
    public void updateRLeaguesEvents(){
        this.getRLeaguesDTODB().forEach(eventService::obtainRLeagueEvents);
    }

    @Scheduled(fixedRate = 2400000) //40 minutes
    public void updateRLeaguesCurrentJour(){
        Utils.esfPrint("Updating current jour for all leagues...");
        this.getRLeaguesDB().forEach(league -> {
            int currentJour = eventService.getCurrentJour(league.getUuid()).getJour();

            if(currentJour == league.getCurrentjour()+1){
                userXLeagueXPlayerService.playerOwnerJourExtension(league);
            }

            league.setCurrentjour(currentJour);
            realLeagueRepository.save(league);
        });
        Utils.esfPrint("Current jour updated for all leagues");
    }

    private void removeObsoleteLeagues(Set<RealLeagueDTO> filteredRLeagues, Set<RealLeagueDTO> rLeaguesDB) {

        Utils.esfPrint("Removing obsolete leagues...",1);
        rLeaguesDB.forEach(league -> {
            boolean leagueFound = filteredRLeagues.stream()
                    .anyMatch(filteredLeague -> leagueMatches(filteredLeague, league));
            if (!leagueFound) {
                Utils.esfPrint("Removing obsolete league" + league.getEvent() +"..." ,2);
                realLeagueRepository.deleteById(league.getUuid());
                Utils.esfPrint("Obsolete league" + league.getEvent() + " removed.",2);
            }
        });
        Utils.esfPrint("Obsolete leagues removed",1);


    }

    private void addNewLeaguesCascade(Set<RealLeagueDTO> filteredRLeagues, Set<RealLeagueDTO> rLeaguesDB) {
        Utils.esfPrint("Adding new leagues...",1);
        filteredRLeagues.forEach(filteredLeague -> {

            if(!rLeaguesDB.contains(filteredLeague)) {
                Optional<RealLeague> opRL;
                RealLeague rl;

                opRL = realLeagueRepository.findByEvent(filteredLeague.getEvent());

                rl = opRL.orElseGet(() -> realLeagueRepository.save(new RealLeague(filteredLeague)));

                try {
                    downloadLeagueImage(rl);
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println("ERROR AL DESCARGAR IMAGEN DE LIGA.");
                }

                teamService.updateTeams(rl);
            }
        });
        Utils.esfPrint("New leagues added",1);
    }

    public int getRLeagueTotalJours(String uuid) {
        RealLeague rl = getRLeague(uuid);
        return this.eventService.getRLeagueTotalJours(rl.getUuid());
    }

    public CurrentJourInfoPOJO getRLeagueCurrentJour(String uuid) {
        RealLeague rl = getRLeague(uuid);
        return this.eventService.getCurrentJour(rl.getUuid());
    }
    // ------- OBTAIN ------- //

    private Set<RealLeagueDTO> obtainAllRLeagues() throws IOException {
        Set<RealLeagueDTO> allLeagues = new HashSet<>();

        allLeagues.addAll(lolApiCaller.getAllLeagues());
        //allLeagues.addAll(counterApiCaller.getAllLeagues());

        return allLeagues;
    }

    private Set<RealLeagueDTO> filterObtainedRLeagues(Set<RealLeagueDTO> allLeagues) {
        Set<RealLeagueDTO> filteredLeagues = new HashSet<>();
        for (RealLeagueDTO league : allLeagues) {           //TODO: SWITCH
            if (league.getGame().equals("LOL")) {
                if ((
                        league.getEvent().contains("LCK") ||
                        league.getEvent().contains("LCS") ||
                        league.getEvent().contains("LEC") ||
                        league.getEvent().contains("LFL") ||
                        league.getEvent().contains("LPL") ||
                        league.getEvent().contains("LVP") ||
                        league.getEvent().contains("LRN") ||
                        league.getEvent().contains("PCL")) &&
                        (!league.getEvent().contains("2nd") &&
                        !league.getEvent().contains("3rd") &&
                        !league.getEvent().contains("Championship") &&
                                !league.getEvent().contains("Placements") &&
                        !league.getEvent().contains("Division 2"))
                        ) {

                    String evt = league.getEvent();
                    String op = league.getOverviewpage();

                    if (league.getEvent().contains(" Playoffs")) {
                        evt = evt.replace(" Playoffs", "");
                        op = op.replace(" Playoffs", "");

                    }

                    if (op.endsWith(" Season")) {
                        int originalLength = op.length();
                        int suffixLength = " Season".length();

                        if (originalLength > suffixLength + 1) {
                            op = op.substring(0, originalLength - suffixLength);
                        }
                    }

                    league.setEvent(evt);
                    league.setOverviewpage(op);

                    league.setTeams(lolApiCaller.getLeagueTeams(league.getOverviewpage()));
                    filteredLeagues.add(league);
                }

            }


        if (league.getGame().equals("CSGO")) {
            if (league.getEvent().contains("ESL"))
                filteredLeagues.add(league);
        }
    }

        return filteredLeagues;
    }

    public void downloadLeagueImage(RealLeague rl) throws IOException {

        String op = rl.getOverviewpage().replace(" ", "_");
        op += "_Season";

        String url = LolApiCaller.getTableImgurl(op, "Tournament");
        op = op.replace("/","_");
        Utils.downloadImage(url,"src/main/resources/media/leagues/" + rl.getUuid() + ".png");

    }


    // ------- DATABASE ------- //
    public Set<RealLeagueDTO> getRLeaguesDTODB() {
        Set<RealLeagueDTO> leaguesDTO = new HashSet<>();

        List<RealLeague> rLeagues = realLeagueRepository.findAll();

        //if(rLeagues.isEmpty()) throw new RuntimeException("1016");

        rLeagues.forEach(league ->
                leaguesDTO.add(new RealLeagueDTO(league))
        );

        return leaguesDTO;
    }

    public Set<RealLeague> getRLeaguesDB() {
        return new HashSet<>(realLeagueRepository.findAll());
    }
    public Set<RealLeagueDTO> getGameRLeaguesDB(String game) {
        Set<RealLeague> leagues;

        leagues = realLeagueRepository.findByGame(game);

        if (leagues.isEmpty()) throw new RuntimeException("1016");

        return leagues.stream()
                .map(RealLeagueDTO::new)
                .collect(Collectors.toSet());
    }

    public RealLeague getRLeague(String uuid) {
        Optional<RealLeague> rlOptional = realLeagueRepository.findById(UUID.fromString(uuid));

        return rlOptional.orElseThrow(() -> new RuntimeException("1017"));
    }

    public byte[] getRLeagueIcon(String uuid){
        Optional<RealLeague> rlOptional = realLeagueRepository.findById(UUID.fromString(uuid));

        RealLeague rl = rlOptional.orElseThrow(() -> new RuntimeException("1017"));

        Path imagePath;
        imagePath = Paths.get("src/main/resources/media/leagues/" + uuid + ".png");

        byte[] imageBytes;

        try {
            imageBytes = Files.readAllBytes(imagePath);

        } catch (IOException e) {
            try {
                imageBytes = Files.readAllBytes(Paths.get("src/main/resources/media/not_found.png"));
            } catch (IOException ioException) {
                throw new RuntimeException();
            }
        }
        return imageBytes;
    }

    public Set<PlayerInfoPOJO> getAllPlayers(String uuid) {
        Set<PlayerInfoPOJO> allPlayers = new HashSet<>();

        RealLeague rl = getRLeague(uuid);

        rl.getTeams().forEach(team -> {
            allPlayers.addAll(teamService.getTeamPlayersInfo(team.getUuid().toString(), uuid));
        });

        return allPlayers;
    }

    // ------- UTILS ------- //

    private boolean leagueMatches(RealLeagueDTO filteredLeague, RealLeagueDTO league) {
        return league.getEvent().equals(filteredLeague.getEvent()) &&
                league.getGame().equals(filteredLeague.getGame()) &&
                Optional.ofNullable(league.getOverviewpage())
                        .map(overviewPage -> overviewPage.equals(filteredLeague.getOverviewpage()))
                        .orElse(true);
    }



}