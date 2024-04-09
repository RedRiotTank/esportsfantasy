package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.model.complexentities.TeamXrLeague;
import htt.esportsfantasybe.repository.complexrepositories.TeamXrLeagueRepository;
import htt.esportsfantasybe.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TeamXrLeagueService {

    @Autowired
    private TeamXrLeagueRepository teamXrLeagueRepository;

    public void linkTeamToLeague(UUID teamID, UUID leagueID) {
        teamXrLeagueRepository.save(new TeamXrLeague(teamID, leagueID));
    }

    public List<TeamXrLeague> getall(){
        return teamXrLeagueRepository.findAll();
    }

    public List<UUID> LeaguesTeamsUUIDs(UUID leagueId){
        return teamXrLeagueRepository.findTeamsByLeagueId(leagueId);
    }




}
