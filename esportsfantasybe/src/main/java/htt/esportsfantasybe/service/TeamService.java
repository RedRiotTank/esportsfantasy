package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.repository.PlayerRepository;
import htt.esportsfantasybe.repository.TeamRepository;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import htt.esportsfantasybe.service.complexservices.TeamXrLeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    private final RealLeagueService realLeagueService;
    private final TeamXrLeagueService teamxrleagueService;


    LolApiCaller lolApiCaller = new LolApiCaller();

    @Autowired
    public TeamService(RealLeagueService realLeagueService, TeamXrLeagueService teamxrleagueService) {
        this.realLeagueService = realLeagueService;
        this.teamxrleagueService = teamxrleagueService;

    }


    public void updateTeam(TeamDTO team, RealLeagueDTO rl){
        Team t;
        List<Team> teamDB = teamRepository.findTeamsByNameAndGame(team.getName(), team.getGame());
        if(teamDB.size() == 1){
            t = teamDB.get(0);
            t.setName(team.getName());
            t.setImage(team.getImage());
            t.setShortname(team.getShortName());
            t.setOverviewpage(team.getOverviewPage());
            t.setGame(team.getGame());
            t = teamRepository.save(t);
        } else {
            t = teamRepository.save(new Team(team));
        }

        teamxrleagueService.linkTeamToLeague(t.getUuid(), realLeagueService.getRLeague(rl).getUuid());



    }

    public void updateTeams() {
        System.out.println("Updating teams");

        realLeagueService.getRLeaguesDB().forEach(league ->{

            switch (league.getGame()){
                case "LOL":
                    lolApiCaller.getLeagueTeams(league.getOverviewpage()).forEach(team -> {
                        updateTeam(team, league);
                    });

                    break;
                case "CSGO":
                    break;
            }
            //System.out.println(rl);
        });
        System.out.println("Updating teams finished");


    }

    public TeamDTO getTeamInfo(UUID teamId) {
        Team team = teamRepository.findById(teamId).get();

        return new TeamDTO(team);

    }

    public List<TeamDTO> getLeagueTeams(UUID leagueId) {

        List<TeamDTO> teamlist = new ArrayList<>();

        List<UUID> teamIds = teamxrleagueService.LeaguesTeamsUUIDs(leagueId);

        teamIds.forEach(teamId -> {
            teamlist.add(getTeamInfo(teamId));
        });


        return teamlist;


    }


}
