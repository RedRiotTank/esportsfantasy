package htt.esportsfantasybe.service;

import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.repository.PlayerRepository;
import htt.esportsfantasybe.repository.TeamRepository;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import htt.esportsfantasybe.service.complexservices.TeamXrLeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;


    private final TeamXrLeagueService teamxrleagueService;


    LolApiCaller lolApiCaller = new LolApiCaller();

    @Autowired
    public TeamService( TeamXrLeagueService teamxrleagueService) {

        this.teamxrleagueService = teamxrleagueService;

    }


    public void updateLeagueTeams(RealLeagueDTO league) {
        Utils.esfPrint("Updating teams for league " + league.getEvent() + "...");

        //List<TeamDTO> filteredTeams = lolApiCaller.getLeagueTeams(league.getOverviewpage());


        switch (league.getGame()){
            case "LOL":
                lolApiCaller.getLeagueTeams(league.getOverviewpage()).forEach(team -> {
                    updateTeam(team, league);
                });

                break;
            case "CSGO":
                break;
        }
        Utils.esfPrint("Finished updating teams for league " + league.getEvent() + ".");

    }


    private void addNewTeam(List<TeamDTO> filteredTeams, List<Team> teamDB){

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

        //teamxrleagueService.linkTeamToLeague(t.getUuid(), realLeagueService.getRLeague(rl).getUuid());



    }



    public void removeObsoleteTeams(RealLeagueDTO league){
        Utils.esfPrint("Removing obsolete teams from league " + league.getEvent() + "...",3);

        league.getTeams().forEach(team -> {
            Utils.esfPrint("Removing team " + team.getName() + " from league " + league.getEvent() + "...",4);
            teamxrleagueService.removeTeamFromLeague(team.getUuid(), league.getUuid());
            removeTeam(team.getUuid());
            Utils.esfPrint("Team " + team.getName() + " removed from league " + league.getEvent() + ".",4);
        });

        /*
        getLeagueTeams(league).forEach(team -> {
            Utils.esfPrint("Removing team " + team.getName() + " from league " + league.getEvent() + "...",4);
            teamxrleagueService.removeTeamFromLeague(team.getUuid(), league.getUuid());
            removeTeam(team.getUuid());
            Utils.esfPrint("Team " + team.getName() + " removed from league " + league.getEvent() + ".",4);
        });

         */
        Utils.esfPrint("Obsolete teams removed from league " + league.getEvent() + ".",3);
    }



    public void removeTeam(UUID teamId) {
        //eliminar jugadores en cascada.
        teamRepository.deleteById(teamId);
    }
    public Set<TeamDTO> getLeagueTeams(RealLeagueDTO league) {
        Set<TeamDTO> teamlist = new HashSet<>();

        teamxrleagueService.getLeagueTeamsUUID(league.getUuid()).forEach(teamId -> {
            teamlist.add(getTeamInfo(teamId));
        });

        return teamlist;
    }

    public TeamDTO getTeamInfo(UUID teamId) {
        return new TeamDTO(teamRepository.findById(teamId).get());
    }







    // ------- UPDATE ------- //
    // ------- OBTAIN ------- //



    // ------- DATABASE ------- //













//General, no derivado, evitar usar.


    /*
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

    */
}
