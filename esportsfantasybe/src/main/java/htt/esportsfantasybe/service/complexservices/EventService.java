package htt.esportsfantasybe.service.complexservices;

import htt.esportsfantasybe.DTO.EventDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.complexentities.Event;
import htt.esportsfantasybe.model.pojos.EventInfoPOJO;
import htt.esportsfantasybe.model.pojos.EventPOJO;
import htt.esportsfantasybe.repository.complexrepositories.EventRepository;
import htt.esportsfantasybe.service.LeagueService;
import htt.esportsfantasybe.service.TeamService;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    private final LolApiCaller lolApiCaller = new LolApiCaller();

    private final TeamService teamService;

    @Autowired
    public EventService(TeamService teamService) {
        this.teamService = teamService;
    }





    public Set<EventInfoPOJO> getEvents(UUID leagueuuid){


        Set<EventInfoPOJO> events = new HashSet<>();

        eventRepository.findAllById_Realleagueuuid(leagueuuid).forEach(
            event ->{

                TeamDTO t1 = teamService.getTeam(event.getId().getTeam1uuid());
                TeamDTO t2 = teamService.getTeam(event.getId().getTeam2uuid());

                events.add(
                        new EventInfoPOJO(
                                event.getId().getTeam1uuid(),
                                event.getId().getTeam2uuid(),
                                t1.getName(),
                                t2.getName(),
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

            EventDTO eventDTO = new EventDTO(
                    realLeagueDTO.getUuid(),
                    team1.getUuid(),
                    team2.getUuid(),
                    Integer.parseInt(ev.getJour()),
                    ev.getDateTime(),
                    null

            );

            eventRepository.save(new Event(eventDTO));


        });
    }


}
