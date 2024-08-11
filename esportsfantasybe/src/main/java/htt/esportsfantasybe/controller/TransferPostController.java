package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.model.complexentities.TransferPost;
import htt.esportsfantasybe.model.pojos.TransferPostPOJO;
import htt.esportsfantasybe.service.complexservices.TransferPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.responses.okresponses;

import java.util.List;

@RestController
@RequestMapping("/api/TransferPost")
public class TransferPostController {

    private final TransferPostService transferPostService;

    @Autowired
    public TransferPostController(TransferPostService transferPostService) {
        this.transferPostService = transferPostService;
    }

    @CrossOrigin
    @PostMapping(value ="/getLeagueTransferPosts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLeagueTransferPosts(@RequestBody String leagueuuid) {
        try {
            List<TransferPostPOJO> transferPosts = transferPostService.getLeagueTransferPosts(leagueuuid);

            return okresponses.getLeagueTransferPosts(transferPosts);
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }

}
