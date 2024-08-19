package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.DTO.EventDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.model.complexentities.Event;
import htt.esportsfantasybe.model.complexentities.PlayerPoints;
import htt.esportsfantasybe.model.pojos.CurrentJourInfoPOJO;
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

    public Set<EventInfoPOJO> getEventsPOJO(UUID leagueuuid){


        Set<EventInfoPOJO> events = new HashSet<>();
        eventRepository.findAllById_Realleagueuuid(leagueuuid).forEach(
            event ->{

                TeamDTO t1 = teamService.getTeamDTO(event.getId().getTeam1uuid());
                TeamDTO t2 = teamService.getTeamDTO(event.getId().getTeam2uuid());

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

    public Set<Event> getEvents(UUID leagueuuid){
        return new HashSet<>(eventRepository.findAllById_Realleagueuuid(leagueuuid));
    }
    public void obtainRLeagueEvents(RealLeagueDTO realLeagueDTO) {
        Set<EventPOJO> rLeagueEvents = lolApiCaller.getRLeaguesEvents(realLeagueDTO.getOverviewpage(), realLeagueDTO.isIsplayoff());

        rLeagueEvents.forEach(ev->{

            TeamDTO team1 = teamService.getTeamDataDB(ev.getTeam1());
            TeamDTO team2 = teamService.getTeamDataDB(ev.getTeam2());

            if(team1 == null || team2 == null) return;

            if(ev.getJour().isEmpty())
                System.out.println("Jour is empty");


            int jour = 0;

            if(ev.getMatchId().toLowerCase().contains("play-in")){
                jour = ev.getMatchId().toLowerCase().charAt(ev.getMatchId().length() - 1) - '0';
                jour += 100;
            }
            else if(ev.getMatchId().toLowerCase().contains("semi")) {
                jour = ev.getMatchId().toLowerCase().charAt(ev.getMatchId().length() - 1) - '0';
                jour += 300;
            } else if(ev.getMatchId().toLowerCase().contains("final")) {
                jour = ev.getMatchId().toLowerCase().charAt(ev.getMatchId().length() - 1) - '0';
                jour += 400;
            }
            else
                jour = Integer.parseInt(ev.getJour());

            if(realLeagueDTO.isIsplayoff() && jour < 100){
                jour += 200;
            }

            EventDTO eventDTO = new EventDTO(
                    realLeagueDTO.getUuid(),
                    team1.getUuid(),
                    team2.getUuid(),
                    jour,
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

    public CurrentJourInfoPOJO getCurrentJour(UUID realLeagueUuid){
        Date now = new Date();
        List<Integer> jourList = new ArrayList<>();

        List<Event> events = eventRepository.findAllById_Realleagueuuid(realLeagueUuid);

        events.forEach(event -> {
            if (!jourList.contains(event.getId().getJour())) {
                jourList.add(event.getId().getJour());
            }
        });

        events.removeIf(event -> event.getDate().before(now));

        if (events.isEmpty()) return new CurrentJourInfoPOJO(0,false, null);

        Event closestEvent = null;
        long minDiff = Long.MAX_VALUE;



        int prevPrio = 10;

        for (Event event : events) {
            int prioevent;

            if(event.getMatchid().toLowerCase().contains("semi"))
                prioevent = 1;
            else if(event.getMatchid().toLowerCase().contains("final"))
                prioevent = 0;
            else if(event.getMatchid().toLowerCase().contains("play-in"))
                prioevent = 3;
            else if (event.getMatchid().toLowerCase().contains("playoff"))
                prioevent = 2;
            else
                prioevent = 4;

            if(prevPrio == 10 || prevPrio < prioevent) closestEvent = event;
            else if(prevPrio == prioevent){
                long diff = event.getDate().getTime() - now.getTime();
                if (diff >= 0 && diff < minDiff) {
                    minDiff = diff;
                    closestEvent = event;
                }

            }

            prevPrio = prioevent;
        }
        int currentJour = closestEvent.getId().getJour();
        boolean editable = true;
        List<Event> eventsInCurrentJour = eventRepository.findAllById_RealleagueuuidAndId_Jour(realLeagueUuid, currentJour);

        for(Event e : eventsInCurrentJour){
            if(e.getDate().before(now)){
                editable = false;
                break;
            }
        }


        return new CurrentJourInfoPOJO(currentJour, editable, jourList);
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

    public int getPlayerPoints(UUID playerUuid, String matchId) {
        return playerPointsService.getPointsByPlayerAndMatch(playerUuid, matchId);
    }

    public EventInfoPOJO getCloseEvent(UUID rleagueuuid){

        List<Event> evs = eventRepository.findAllById_Realleagueuuid(rleagueuuid);
        Date now = new Date();
        Date evDate = new Date(Long.MAX_VALUE);

        Event closeEvent = null;

        for(Event ev : evs){

            if(ev.getDate().after(now) && ev.getDate().before(evDate)){
                evDate = ev.getDate();
                closeEvent = ev;
            }
        }

        if(closeEvent == null) return null;

        String team1Icon = Base64.getEncoder().encodeToString(teamService.getTeamIcon(closeEvent.getId().getTeam1uuid()));
        String team2Icon = Base64.getEncoder().encodeToString(teamService.getTeamIcon(closeEvent.getId().getTeam2uuid()));

        return new EventInfoPOJO(
                closeEvent.getId().getTeam1uuid(),
                closeEvent.getId().getTeam2uuid(),
                teamService.getTeamDTO(closeEvent.getId().getTeam1uuid()).getName(),
                teamService.getTeamDTO(closeEvent.getId().getTeam2uuid()).getName(),
                team1Icon,
                team2Icon,
                closeEvent.getTeam1Score(),
                closeEvent.getTeam2Score(),
                closeEvent.getId().getJour(),
                closeEvent.getDate()
        );

    }
}
