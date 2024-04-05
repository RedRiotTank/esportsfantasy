package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.repository.RealLeagueRepository;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RealLeagueService {
    @Autowired
    private RealLeagueRepository realLeagueRepository;
    private final LolApiCaller lolApiCaller = new LolApiCaller();



    @Scheduled(cron = "5  35  4  */10  *  *")
    public void updateLeagues() {
        System.out.println("Updating leagues...");
        List<RealLeagueDTO> filteredLeaguesAPI = filterLeagues(lolApiCaller.getAllLeagues());

        List<RealLeague> leaguesDB =  realLeagueRepository.findAll();


        for (RealLeagueDTO filteredLeague : filteredLeaguesAPI) {
            boolean found = false;
            for (RealLeague league : leaguesDB) {
                if (league.getEvent().equals(filteredLeague.getEvent()) &&
                        league.getOverviewpage().equals(filteredLeague.getOverviewpage()) &&
                        league.getGame().equals(filteredLeague.getGame())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                RealLeague newLeague = new RealLeague(filteredLeague.getEvent(), filteredLeague.getOverviewpage(), filteredLeague.getGame());
                realLeagueRepository.save(newLeague);
            }
        }

        for (RealLeague league : leaguesDB) {
            boolean found = false;
            for (RealLeagueDTO filteredLeague : filteredLeaguesAPI) {
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


    private List<RealLeagueDTO> filterLeagues(ArrayList<RealLeagueDTO> allLeagues) {
        List<RealLeagueDTO> filteredLeagues = new ArrayList<>();
        for (RealLeagueDTO league : allLeagues) {
            if (league.getGame().equals("LOL")) {
                if ((league.getEvent().contains("EBL") ||
                    league.getEvent().contains("LCK") ||
                    league.getEvent().contains("LCS") ||
                    league.getEvent().contains("LEC") ||
                    league.getEvent().contains("LFL") ||
                    league.getEvent().contains("LPL") ||
                    league.getEvent().contains("LVP") ||
                    league.getEvent().contains("LRN") ||
                    league.getEvent().contains("PCL")) &&
                    (!league.getEvent().contains("2nd") ||
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
        }
        return filteredLeagues;
    }

}