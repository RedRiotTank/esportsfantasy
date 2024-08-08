package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.responses.okresponses;
import htt.esportsfantasybe.service.complexservices.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @CrossOrigin
    @PostMapping(value ="/getEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEvents(@RequestBody String leagueuuid) {
        try {
            return okresponses.getEvents(eventService.getEventsPOJO(UUID.fromString(leagueuuid)));
        } catch (Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }


}
