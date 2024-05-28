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

    public void linkUserToLeague(UUID userID, UUID leagueID, boolean isAdmin, int money) {
           userXLeagueRepository.save(new UserXLeague(new UserXLeagueId(userID, leagueID), isAdmin, money));
    }

    public boolean isUserInLeague(UUID userID, UUID leagueID) {
        return userXLeagueRepository.existsById(new UserXLeagueId(userID, leagueID));
    }

    public List<UserXLeague> getUserXLeaguesForUser(UUID userUuid) {
       return userXLeagueRepository.findAllByUserUuid(userUuid);
    }

    public int getUserXLeagueMoney(UUID userUuid, UUID leagueUuid) {
        UserXLeague res =  userXLeagueRepository.findById(new UserXLeagueId(userUuid, leagueUuid)).orElseThrow(() -> new RuntimeException("1020"));
        return res.getMoney();
    }

    public boolean userHasEnoughMoney(UUID userUuid, UUID leagueUuid, int money) {
        return getUserXLeagueMoney(userUuid, leagueUuid) >= money;
    }

    public void addMoney(UUID userUuid, UUID leagueUuid, int money){
        userXLeagueRepository.findById(new UserXLeagueId(userUuid, leagueUuid)).ifPresent(userXLeague -> {
            userXLeague.setMoney(userXLeague.getMoney() + money);
            userXLeagueRepository.save(userXLeague);
        });
    }

    public void discountMoney(UUID userUuid, UUID leagueUuid, int money){
        if(userHasEnoughMoney(userUuid, leagueUuid, money)){
            userXLeagueRepository.findById(new UserXLeagueId(userUuid, leagueUuid)).ifPresent(userXLeague -> {
                userXLeague.setMoney(userXLeague.getMoney() - money);
                userXLeagueRepository.save(userXLeague);
            });
        } else {
            throw new RuntimeException("1022");
        }
    }
}
