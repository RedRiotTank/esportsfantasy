package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.User;
import htt.esportsfantasybe.repository.RealLeagueRepository;
import htt.esportsfantasybe.service.apicaller.CounterApiCaller;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import jakarta.transaction.Transactional;
import jdk.jshell.execution.Util;
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

    @Autowired
    public RealLeagueService(TeamService teamService) {
        this.teamService = teamService;
    }


    // ------- UPDATE ------- //

    @Scheduled(cron = "5  35  4  */10  *  *")
    @Transactional
    public void updateLeagues() throws IOException {
        Utils.esfPrint("Updating leagues...");

        Set<RealLeagueDTO> filteredRLeagues = filterObtainedRLeagues(obtainAllRLeagues());
        Set<RealLeagueDTO> rLeaguesDB = getRLeaguesDB();

        removeObsoleteLeagues(filteredRLeagues, rLeaguesDB);        //with derivations.
        addNewLeagues(filteredRLeagues);                            //with derivations.

        Utils.esfPrint("Leagues updated");
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

    private void addNewLeagues(Set<RealLeagueDTO> filteredRLeagues) {
        Utils.esfPrint("Adding new leagues...",1);
        filteredRLeagues.forEach(filteredLeague -> {

            Optional<RealLeague> opRL;
            RealLeague rl;

            try {
                downloadLeagueImage(filteredLeague.getOverviewpage());
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("ERROR AL DESCARGAR IMAGEN DE LIGA.");
            }

            opRL = realLeagueRepository.findByEvent(filteredLeague.getEvent());

            rl = opRL.orElseGet(() -> realLeagueRepository.save(new RealLeague(filteredLeague)));

            teamService.updateTeams(rl);
        });
        Utils.esfPrint("New leagues added",1);
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
        for (RealLeagueDTO league : allLeagues) {
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

    public void downloadLeagueImage(String overviewpage) throws IOException {

        String op = overviewpage.replace(" ", "_");
        op += "_Season";

        String url = LolApiCaller.getTableImgurl(op, "Tournament");
        op = op.replace("/","_");
        Utils.downloadImage(url,"src/main/resources/media/LOL/leagues/" + op + ".png");

    }


    // ------- DATABASE ------- //
    public Set<RealLeagueDTO> getRLeaguesDB() {
        Set<RealLeagueDTO> leagues = new HashSet<>();

        realLeagueRepository.findAll().forEach(league ->
                leagues.add(new RealLeagueDTO(league))
        );

        return leagues;
    }

    public Set<RealLeagueDTO> getGameRLeaguesDB(String game) {
        Set<RealLeague> leagues = new HashSet<>();

        leagues = realLeagueRepository.findByGame(game);

        return leagues.stream()
                .map(RealLeagueDTO::new)
                .collect(Collectors.toSet());
    }



    public RealLeague getRLeague(String uuid) {
        Optional<RealLeague> rlOptional = realLeagueRepository.findById(UUID.fromString(uuid));

        RealLeague rl = rlOptional.orElseThrow(() -> new RuntimeException("liga no encontrada"));

        return rl;
    }

    public byte[] getRLeagueIcon(String uuid) throws IOException {
        Optional<RealLeague> rlOptional = realLeagueRepository.findById(UUID.fromString(uuid));



        RealLeague rl = rlOptional.orElseThrow(() -> new RuntimeException("liga no encontrada"));


        Path imagePath;
        imagePath = Paths.get("src/main/resources/media/" + rl.getGame() + "/leagues/" + Utils.generateOPname(rl.getOverviewpage()) + ".png");

        byte[] imageBytes;

        try {
            imageBytes = Files.readAllBytes(imagePath);

        } catch (IOException e) {
            try {
                imageBytes = Files.readAllBytes(Paths.get("src/main/resources/media/not_found.png"));
            } catch (IOException ioException) {
                throw new RuntimeException("numerr");
            }
        }
        return imageBytes;
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