package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.Player;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.complexentities.Event;
import htt.esportsfantasybe.model.complexentities.UserXLeagueXPlayer;
import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueXPlayerId;
import htt.esportsfantasybe.model.pojos.PlayerTeamInfoPOJO;
import htt.esportsfantasybe.model.pojos.TeamAllComponentsPOJO;
import htt.esportsfantasybe.repository.LeagueRepository;
import htt.esportsfantasybe.repository.complexrepositories.UserXLeagueXPlayerRepository;
import htt.esportsfantasybe.service.PlayerService;
import htt.esportsfantasybe.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserXLeagueXPlayerService {

    @Autowired
    private UserXLeagueXPlayerRepository userXLeagueXPlayerRepository;

    private LeagueRepository leagueRepository;

    private PlayerService playerService;

    private EventService eventService;

    private TeamService teamService;


    @Autowired
    public UserXLeagueXPlayerService(
        PlayerService playerService,
        LeagueRepository leagueRepository,
        EventService eventService,
        TeamService teamService) {

        this.playerService = playerService;
        this.leagueRepository = leagueRepository;
        this.eventService = eventService;
        this.teamService = teamService;
    }


    //TODO: hardcoded jornada y alineamiento.
    public void linkUserLeaguePlayer(UUID userID, UUID leagueID, UUID playerID, int jour) {
        userXLeagueXPlayerRepository.save(new UserXLeagueXPlayer(new UserXLeagueXPlayerId(playerID, leagueID, userID,jour), 0));
    }

    public void unlinkAllUserLeaguePlayer(UUID userID, UUID leagueID) {
        Set<UserXLeagueXPlayer> userXLeagueXPlayers = userXLeagueXPlayerRepository.findAllById_UseruuidAndId_Leagueuuid(userID, leagueID);

        userXLeagueXPlayerRepository.deleteAll(userXLeagueXPlayers);
    }

    public void changeProperty(UUID oldUser, UUID newUser, UUID leagueID, UUID playerID, int jour) {

        UserXLeagueXPlayer oldprop = userXLeagueXPlayerRepository.findById_PlayeruuidAndId_LeagueuuidAndId_UseruuidAndId_Jour(
                playerID, leagueID, oldUser, jour);

        UserXLeagueXPlayer newprop = new UserXLeagueXPlayer(new UserXLeagueXPlayerId(playerID, leagueID, newUser, jour), 0);

        userXLeagueXPlayerRepository.delete(oldprop);
         
        userXLeagueXPlayerRepository.save(newprop);

    }

    public TeamAllComponentsPOJO getUserXLeagueTeam(UUID userID, UUID leagueID) {
        List<UserXLeagueXPlayer> teamres = this.userXLeagueXPlayerRepository.findAllById_LeagueuuidAndId_Useruuid(leagueID, userID);

        HashMap<String, String> resourcesPlayerIcons = new HashMap<>();
        HashMap<String, String> resourcesTeamIcons = new HashMap<>();

        Set<PlayerTeamInfoPOJO> team = new HashSet<>();

        League league = leagueRepository.findById(leagueID).get();

        Set<Event> evs = eventService.getEvents(league.getRealLeague().getUuid());

        teamres.forEach(entry -> {
            UUID playerUUID = entry.getId().getPlayeruuid();

            Player player = playerService.getPlayer(entry.getId().getPlayeruuid());
            AtomicInteger pp = new AtomicInteger(-999);

            evs.forEach(ev -> {
                if (ev.getId().getJour() == entry.getId().getJour()) {
                    player.getTeams().forEach(t -> {
                        if ((t.getUuid().equals(ev.getId().getTeam1uuid()) || t.getUuid().equals(ev.getId().getTeam2uuid()))
                                && (!ev.getTeam1Score().equals("null")  && !ev.getTeam2Score().equals("null"))) {

                            pp.set(eventService.getPlayerPoints(player.getUuid(), ev.getMatchid()));

                        }
                    });
                }
            });

            String playericonB64 = Base64.getEncoder().encodeToString(playerService.getPlayerIcon(playerUUID.toString()));

            //Team icon
            AtomicReference<String> rtUUID = new AtomicReference<>("");
            player.getTeams().forEach(t -> {
                league.getRealLeague().getTeams().forEach(rt -> {
                    if (t.getUuid().equals(rt.getUuid())) {
                        resourcesTeamIcons.put(rt.getUuid().toString(), Base64.getEncoder().encodeToString(teamService.getTeamIcon(t.getUuid())));
                        rtUUID.set(rt.getUuid().toString());
                    }
                });
            });

            //Player icon
            resourcesPlayerIcons.put(playerUUID.toString(), playericonB64);


            PlayerTeamInfoPOJO ptInfoPOJo = new PlayerTeamInfoPOJO(
                    player.getUuid(),
                    player.getUsername(),
                    player.getFullname(),
                    player.getRole(),
                    player.getValue(),
                    entry.getId().getJour(),
                    entry.getAligned(),
                    pp.get(),
                    rtUUID.get()
            );
            team.add(ptInfoPOJo);
        });

        return new TeamAllComponentsPOJO(team, resourcesPlayerIcons, resourcesTeamIcons);
    }

    public void setAligned(UUID userID, UUID leagueID, UUID playerID, int aligned) {
        int currentJour = leagueRepository.findById(leagueID).get().getRealLeague().getCurrentjour() + 1;

        UserXLeagueXPlayer old = userXLeagueXPlayerRepository.findById_LeagueuuidAndId_UseruuidAndId_JourAndAligned(leagueID, userID, currentJour, aligned);

        if(old != null){
            old.setAligned(0);
            userXLeagueXPlayerRepository.save(old);
        }

        UserXLeagueXPlayerId newId = new UserXLeagueXPlayerId(playerID, leagueID, userID, currentJour);

        UserXLeagueXPlayer userXLeagueXPlayer = userXLeagueXPlayerRepository.findById(newId).get();
        userXLeagueXPlayer.setAligned(aligned);
        userXLeagueXPlayerRepository.save(userXLeagueXPlayer);
    }

    public void playerOwnerJourExtension(RealLeague rLeague){
        System.out.println("Jour player extension");

        Set<League> leagueset = leagueRepository.findAllByRealLeague(rLeague);

        leagueset.forEach(league -> {
            userXLeagueXPlayerRepository.findAllById_Leagueuuid(league.getUuid()).forEach(
                    userXLeagueXPlayer -> {
                        UserXLeagueXPlayer extension  = new UserXLeagueXPlayer(
                                userXLeagueXPlayer.getId().getPlayeruuid(),
                                userXLeagueXPlayer.getId().getLeagueuuid(),
                                userXLeagueXPlayer.getId().getUseruuid(),
                                userXLeagueXPlayer.getId().getJour() + 1,
                                userXLeagueXPlayer.getAligned()
                        );

                        userXLeagueXPlayerRepository.save(extension);
                    }
            );
        });
    }

    public Set<UserXLeagueXPlayer> getUserxLeague(UUID user, UUID league){
        return new HashSet<>(userXLeagueXPlayerRepository.findAllById_UseruuidAndId_Leagueuuid(user, league));
    }


}

