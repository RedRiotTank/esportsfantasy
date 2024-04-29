package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.model.complexentities.TeamXrLeague;
import htt.esportsfantasybe.model.complexentities.UserXLeague;
import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueId;
import htt.esportsfantasybe.repository.complexrepositories.UserXLeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserXLeagueService {

    @Autowired
    private UserXLeagueRepository userXLeagueRepository;

    public void linkUserToLeague(UUID userID, UUID leagueID, boolean isAdmin) {
           userXLeagueRepository.save(new UserXLeague(new UserXLeagueId(userID, leagueID), isAdmin));
    }

    public boolean isUserInLeague(UUID userID, UUID leagueID) {
        return userXLeagueRepository.existsById(new UserXLeagueId(userID, leagueID));
    }

    public List<UserXLeague> getUserXLeaguesForUser(UUID userUuid) {
       return userXLeagueRepository.findAllByUserUuid(userUuid);
    }

}
