package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.model.complexentities.TeamXPlayer;
import htt.esportsfantasybe.repository.complexrepositories.TeamXPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TeamXPlayerService {

    @Autowired
    private TeamXPlayerRepository teamXPlayerRepository;

    public void linkTeamToPlayer(UUID teamID, UUID playerID) {
        teamXPlayerRepository.save(new TeamXPlayer(teamID, playerID));
    }

}
