package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.repository.PlayerRepository;
import htt.esportsfantasybe.repository.TeamRepository;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import htt.esportsfantasybe.service.complexservices.TeamXPlayerService;
import htt.esportsfantasybe.service.complexservices.TeamXrLeagueService;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;


    @Getter
    private final TeamXrLeagueService teamxrleagueService;

    @Getter
    private final TeamXPlayerService teamxplayerService;


    LolApiCaller lolApiCaller = new LolApiCaller();

    @Autowired
    public TeamService( TeamXrLeagueService teamxrleagueService, TeamXPlayerService teamxplayerService) {

        this.teamxrleagueService = teamxrleagueService;
        this.teamxplayerService = teamxplayerService;

    }


    public TeamDTO getTeamDataDB(){
        Set<Team> team = teamRepository.findTeamsByNameAndGame("Invictus Gaming", "LOL");

        return new TeamDTO(team.stream().findFirst().get());

    }
}
