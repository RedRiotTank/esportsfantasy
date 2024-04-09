package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.repository.RealLeagueRepository;
import htt.esportsfantasybe.service.apicaller.CounterApiCaller;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RealLeagueService {
    @Autowired
    private RealLeagueRepository realLeagueRepository;
    private final LolApiCaller lolApiCaller = new LolApiCaller();
    private final CounterApiCaller counterApiCaller = new CounterApiCaller();



    @Scheduled(cron = "5  35  4  */10  *  *")
    public void updateLeagues() throws IOException {
        System.out.println("Updating leagues...");


        List<RealLeagueDTO> filteredRLeagues = filterRLeagues(obtainAllRLeagues());

        List<RealLeague> rLeaguesDB =  realLeagueRepository.findAll();

        for (RealLeagueDTO filteredLeague : filteredRLeagues) {
            boolean found = false;
            for (RealLeague league : rLeaguesDB) {
                if (league.getEvent().equals(filteredLeague.getEvent()) &&
                        league.getGame().equals(filteredLeague.getGame())) {

                    if(league.getOverviewpage() != null){
                        if(league.getOverviewpage().equals(filteredLeague.getOverviewpage())){
                            found = true;
                            break;
                        }
                    } else {
                        found = true;
                        break;
                    }

                }
            }
            if (!found) {
                RealLeague newLeague = new RealLeague(filteredLeague.getEvent(), filteredLeague.getOverviewpage(), filteredLeague.getShortname(),filteredLeague.getGame(), filteredLeague.getApiId());
                realLeagueRepository.save(newLeague);
            }
        }

        for (RealLeague league : rLeaguesDB) {
            boolean found = false;
            for (RealLeagueDTO filteredLeague : filteredRLeagues) {
                if (league.getEvent().equals(filteredLeague.getEvent()) &&
                        league.getOverviewpage().equals(filteredLeague.getOverviewpage()) &&
                        league.getGame().equals(filteredLeague.getGame())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                realLeagueRepository.delete(league);
            }
        }

        System.out.println("Leagues update finished.");
    }

    private List<RealLeagueDTO> obtainAllRLeagues() throws IOException {
        ArrayList<RealLeagueDTO> allLeagues = new ArrayList<>();
        allLeagues.addAll(lolApiCaller.getAllLeagues());
        //allLeagues.addAll(counterApiCaller.getAllLeagues());
        return allLeagues;
    }


    private List<RealLeagueDTO> filterRLeagues(List<RealLeagueDTO> allLeagues) {
        List<RealLeagueDTO> filteredLeagues = new ArrayList<>();
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
    public List<RealLeagueDTO> getRLeaguesDB() {
        ArrayList<RealLeagueDTO> leagues = new ArrayList<>();
        realLeagueRepository.findAll().forEach(league -> leagues.add(new RealLeagueDTO(league.getUuid(),league.getEvent(), league.getOverviewpage(), league.getShortname(),league.getGame())));
        return leagues;
    }

    public RealLeague getRLeague(RealLeagueDTO league){
        return realLeagueRepository.findByEventAndOverviewpageAndGame(league.getEvent(), league.getOverviewpage(), league.getGame());

    }

}