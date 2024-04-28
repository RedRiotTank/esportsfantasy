package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.pojos.JoinLeaguePOJO;
import htt.esportsfantasybe.repository.LeagueRepository;
import htt.esportsfantasybe.repository.RealLeagueRepository;
import htt.esportsfantasybe.service.complexservices.UserXLeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;

    private UserService userService;
    private UserXLeagueService userXLeagueService;

    @Autowired
    public LeagueService( UserService userService, UserXLeagueService userXLeagueService) {
        this.userService = userService;
        this.userXLeagueService = userXLeagueService;
    }


    public void joinLeague(JoinLeaguePOJO joinLeaguePOJO){
        switch (joinLeaguePOJO.getLeagueType()){
            case 1:
                League league = leagueRepository.save(new League(joinLeaguePOJO.getLeagueName(), joinLeaguePOJO.isClauseActive(), joinLeaguePOJO.getStartType()));

                UserDTO creatorDTO = userService.getUser(joinLeaguePOJO.getUserMail());
                userXLeagueService.linkUserToLeague(creatorDTO.getUuid(), league.getUuid(), true);

            case 2:



                break;
            case 3:

            default:
                System.out.println("Invalid league type");
        }



    }


    public Set<League> getLeaguesWithTournament(UUID tournamentID){



        return null;
    }


}
