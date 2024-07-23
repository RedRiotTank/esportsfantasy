package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.DTO.EventDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.model.complexentities.Event;
import htt.esportsfantasybe.model.complexentities.PlayerPoints;
import htt.esportsfantasybe.model.pojos.EventInfoPOJO;
import htt.esportsfantasybe.model.pojos.EventPOJO;
import htt.esportsfantasybe.repository.complexrepositories.EventRepository;
import htt.esportsfantasybe.service.TeamService;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    private final LolApiCaller lolApiCaller = new LolApiCaller();

    private final TeamService teamService;
    private final PlayerPointsService playerPointsService;

    @Autowired
    public EventService(TeamService teamService, PlayerPointsService playerPointsService) {
        this.teamService = teamService;
        this.playerPointsService = playerPointsService;
    }



    public int getRLeagueTotalJours(UUID leagueuuid){
        return eventRepository.findMaxJour(leagueuuid);
    }

    public Set<EventInfoPOJO> getEvents(UUID leagueuuid){


        Set<EventInfoPOJO> events = new HashSet<>();
        eventRepository.findAllById_Realleagueuuid(leagueuuid).forEach(
            event ->{

                TeamDTO t1 = teamService.getTeam(event.getId().getTeam1uuid());
                TeamDTO t2 = teamService.getTeam(event.getId().getTeam2uuid());

                byte[] t1icon = teamService.getTeamIcon(t1.getUuid());
                byte[] t2icon = teamService.getTeamIcon(t2.getUuid());

                String t1iconBase64 = Base64.getEncoder().encodeToString(t1icon);
                String t2iconBase64 = Base64.getEncoder().encodeToString(t2icon);

                events.add(
                        new EventInfoPOJO(
                                event.getId().getTeam1uuid(),
                                event.getId().getTeam2uuid(),
                                t1.getName(),
                                t2.getName(),
                                t1iconBase64,
                                t2iconBase64,
                                event.getTeam1Score(),
                                event.getTeam2Score(),
                                event.getId().getJour(),
                                event.getDate()
                        )
                );
            }
        );




        return events;
    }

    public void obtainRLeagueEvents(RealLeagueDTO realLeagueDTO) {
        Set<EventPOJO> rLeagueEvents = lolApiCaller.getRLeaguesEvents(realLeagueDTO.getOverviewpage());

        rLeagueEvents.forEach(ev->{

            TeamDTO team1 = teamService.getTeamDataDB(ev.getTeam1());
            TeamDTO team2 = teamService.getTeamDataDB(ev.getTeam2());

            if(team1 == null || team2 == null) return;

            if(ev.getJour().isEmpty())
                ev.setJour("-33");

            EventDTO eventDTO = new EventDTO(
                    realLeagueDTO.getUuid(),
                    team1.getUuid(),
                    team2.getUuid(),
                    Integer.parseInt(ev.getJour()),
                    ev.getDateTime(),
                    ev.getTeam1Score(),
                    ev.getTeam2Score(),
                    ev.getMatchId(),
                    ev.getMvp()

            );

            Event savedEv = eventRepository.save(new Event(eventDTO));

            if(savedEv.getTeam1Score() != "null" && savedEv.getTeam2Score() != "null"){
                switch (realLeagueDTO.getGame()){
                    case "LOL":
                        playerPointsService.obtainLOLPoints(savedEv);
                        break;

                }
            }


        });
    }

    public int getCurrentJour(UUID realLeagueUuid){
        Date now = new Date();

        List<Event> events = eventRepository.findAllById_Realleagueuuid(realLeagueUuid);

        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event event = iterator.next();
            if (event.getDate().before(now)) {
                iterator.remove();
            }
        }

        if (events.isEmpty()) return 0;

        Event closestEvent = null;
        long minDiff = Long.MAX_VALUE;

        for (Event event : events) {
            long diff = event.getDate().getTime() - now.getTime();
            if (diff >= 0 && diff < minDiff) {
                minDiff = diff;
                closestEvent = event;
            }
        }
        return closestEvent.getId().getJour();
    }

    public ArrayList<Integer> getPlayerPointsHistory(UUID playerUUID) {
        List<PlayerPoints> playerPointsList = playerPointsService.getpointsByPlayer(playerUUID);

        List<String> matchIds = playerPointsList.stream()
                .map(playerPoints -> playerPoints.getId().getMatchid())
                .collect(Collectors.toList());

        List<Event> events = eventRepository.findByMatchidIn(matchIds);

        Map<String, Date> matchDateMap = events.stream()
                .collect(Collectors.toMap(Event::getMatchid, Event::getDate));

        playerPointsList.sort((pp1, pp2) -> matchDateMap.get(pp2.getId().getMatchid()).compareTo(matchDateMap.get(pp1.getId().getMatchid())));

        ArrayList<Integer> pointsHistory = playerPointsList.stream()
                .map(PlayerPoints::getPoints)
                .collect(Collectors.toCollection(ArrayList::new));

        return pointsHistory;
    }
}
