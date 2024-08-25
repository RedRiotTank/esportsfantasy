package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.model.pojos.*;
import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.responses.okresponses;
import htt.esportsfantasybe.service.complexservices.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

            return okresponses.bidup();
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping(value ="/cancelBidup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelBid(@RequestBody BidupPOJO bidup){
        try {
            marketService.cancelBid(bidup.getPlayeruuid(), bidup.getLeagueuuid(), bidup.getUseruuid());

            return okresponses.bidup();
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping(value ="/sell", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sell(@RequestBody BidupPOJO bidup) {
        try {
            marketService.sell(bidup.getPlayeruuid(), bidup.getLeagueuuid(), bidup.getUseruuid(), bidup.getValue());

            return okresponses.sell();
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }


    @CrossOrigin
    @PostMapping(value ="/cancelSell", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelSell(@RequestBody BidupPOJO bidup){
        try {
            marketService.cancelSell(bidup.getPlayeruuid(), bidup.getLeagueuuid(), bidup.getUseruuid(), bidup.getValue());

            return okresponses.sell();
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }


    @CrossOrigin
    @PostMapping(value ="/getOffers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOffers(@RequestBody UserLeaguePOJO userLeaguePOJO){
        try {
            List< UserOffer> offers = marketService.getOffers(userLeaguePOJO.getUseruuid(), userLeaguePOJO.getLeagueuuid());

            return okresponses.getOffers(offers);
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }


    @CrossOrigin
    @PostMapping(value ="/acceptOffer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptOffer(@RequestBody OfferResponsePOJO offerResponsePOJO){
        try {
            marketService.acceptOffer(offerResponsePOJO);

            return okresponses.acceptOffer();
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping(value ="/rejectOffer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> rejectOffer(@RequestBody OfferResponsePOJO offerResponsePOJO){
        try {
            marketService.rejectOffer(offerResponsePOJO);

            return okresponses.rejectOffer();
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping(value ="/clausePlayer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> clausePlayer(@RequestBody ClausePOJO clausePOJO){
        try {
            marketService.clausePlayer(clausePOJO);

            return okresponses.clausePlayer();
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping(value ="/increaseClause", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> increaseClause(@RequestBody IncreaseClausePOJO increaseClausePOJO){
        try {
            marketService.increaseClause(increaseClausePOJO);

            return okresponses.clausePlayer();
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }
}


