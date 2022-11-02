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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bet")

public class BetController {

    @Autowired
    BetRepository betRepository;


    @GetMapping(value = "/bet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER') or hasRole('ROLE_SPECIALIST') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> registerBet(@RequestBody BetRequest betRequest, @AuthenticationPrincipal User user) {

        if(betRepository.existsByNifAndIdGame((int) user.getNif(), betRequest.getIdGame())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: invalid bet!"));
        }

        Bet bet = new Bet((int) user.getNif(),
                betRequest.getValue(),
                betRequest.getSate(),
                betRequest.getOdd(),
                betRequest.getIdGame());

        betRepository.save(bet);

        return ResponseEntity.ok(new MessageResponse("Bet created successfully!"));
    }


}