package htt.esportsfantasybe.service;

import htt.esportsfantasybe.repository.LeagueRepository;
import htt.esportsfantasybe.repository.RealLeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;
    public LeagueService() {
    }




}
