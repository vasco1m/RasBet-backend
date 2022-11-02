package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.exception.AgeException;
import net.java.rasbetbackend.model.*;
import net.java.rasbetbackend.payload.request.BetRequest;
import net.java.rasbetbackend.payload.request.SignupRequest;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bet")

public class BetController {

    @Autowired
    BetGameRepository betGameRepository;

    @Autowired
    BetRepository betRepository;

    @Autowired
    BetSingleRepository betSingleRepository;


    @GetMapping(value = "/bet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER') or hasRole('ROLE_SPECIALIST') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> registerBet(@RequestBody BetRequest betRequest) {
        if(betRepository.existsByIdBet(betRequest.getIdBet())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Bet is already registered!"));
        }

        try {
            //Assumindo que todas são betsingle
            Bet bet = new Bet(betRequest.getIdBet(),
                    betRequest.getIdGame(),
                    betRequest.getOdd(),
                    betRequest.getIdParticipant());

            //betSingleRepository.add(bet);
        }
    }


}