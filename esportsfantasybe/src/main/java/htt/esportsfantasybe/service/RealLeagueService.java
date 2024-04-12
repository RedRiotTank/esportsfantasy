package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.repository.RealLeagueRepository;
import htt.esportsfantasybe.service.apicaller.CounterApiCaller;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
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





/*
    private void addNewLeagues(List<RealLeagueDTO> filteredRLeagues, List<RealLeague> rLeaguesDB) {
        Utils.esfPrint("Adding new leagues...",1);
        filteredRLeagues.forEach(filteredLeague -> {
            RealLeague newLeague = new RealLeague(filteredLeague.getEvent(),
                filteredLeague.getOverviewpage(), filteredLeague.getShortname(),
                    filteredLeague.getGame(), filteredLeague.getApiId());
            realLeagueRepository.save(newLeague);
            //teamService.updateLeagueTeams(new RealLeagueDTO(newLeague));      //cascada equpos
        });
        Utils.esfPrint("New leagues added",1);
    }
    */




    // ------- UPDATE ------- //


    @Scheduled(cron = "5  35  4  */10  *  *")
    public void updateLeagues() throws IOException {
        Utils.esfPrint("Updating leagues...");

        Set<RealLeagueDTO> filteredRLeagues = filterObtainedRLeagues(obtainAllRLeagues());
        Set<RealLeagueDTO> rLeaguesDB = getRLeaguesDB();

        removeObsoleteLeagues(filteredRLeagues, rLeaguesDB);    //with derivations.
        //addNewLeagues(filteredRLeagues, rLeaguesDB);            //with derivations.


        Utils.esfPrint("Leagues updated");
    }

    private void removeObsoleteLeagues(Set<RealLeagueDTO> filteredRLeagues, Set<RealLeagueDTO> rLeaguesDB) {

        Utils.esfPrint("Removing obsolete leagues...",1);
        rLeaguesDB.forEach(league -> {
            boolean leagueFound = filteredRLeagues.stream()
                    .anyMatch(filteredLeague -> leagueMatches(filteredLeague, league));
            if (!leagueFound) {
                Utils.esfPrint("Removing obsolete league" + league.getEvent() +"..." ,2);
                teamService.removeObsoleteTeams(league);   //cascade delete team
                realLeagueRepository.deleteById(league.getUuid());
                Utils.esfPrint("Obsolete league" + league.getEvent() + "removed.",2);
            }
        });
        Utils.esfPrint("Obsolete leagues removed",1);


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
                                !league.getEvent().contains("3rd"))) {

                    if (league.getEvent().contains(" Playoffs")) {
                        String eventtNoPlayoffs = league.getEvent().replace(" Playoffs", "");
                        String overviewPageNoPlayoffs = league.getOverviewpage().replace(" Playoffs", "");
                        league.setEvent(eventtNoPlayoffs);
                        league.setOverviewpage(overviewPageNoPlayoffs);
                    }

                    league.setTeams(lolApiCaller.getLeagueTeams(league.getOverviewpage()));
                    filteredLeagues.add(league);
                }
            }

            if(league.getGame().equals("CSGO")){
                if(league.getEvent().contains("ESL"))
                    filteredLeagues.add(league);
            }
        }
        return filteredLeagues;
    }


    // ------- DATABASE ------- //
    public Set<RealLeagueDTO> getRLeaguesDB() {
        Set<RealLeagueDTO> leagues = new HashSet<>();

        realLeagueRepository.findAll().forEach(league ->
                leagues.add(new RealLeagueDTO(league))
        );

        return leagues;
    }

    public RealLeague getRLeague(RealLeagueDTO league){
        return realLeagueRepository.findByEventAndOverviewpageAndGame(league.getEvent(), league.getOverviewpage(), league.getGame());

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