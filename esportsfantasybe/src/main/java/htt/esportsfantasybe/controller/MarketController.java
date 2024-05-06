package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.model.pojos.BidupPOJO;
import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.responses.okresponses;
import htt.esportsfantasybe.service.complexservices.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Market")
public class MarketController {

    private final MarketService marketService;

    @Autowired
    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }


    @CrossOrigin
    @PostMapping(value ="/bidup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bidup(@RequestBody BidupPOJO bidup) {
        try {
            marketService.bidUp(bidup.getPlayeruuid(), bidup.getLeagueuuid(), bidup.getUseruuid(), bidup.getValue());
            System.out.println("bidup");

            return okresponses.bidup();
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }
}
