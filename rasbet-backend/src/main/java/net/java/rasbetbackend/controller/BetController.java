package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.exception.AgeException;
import net.java.rasbetbackend.model.*;
import net.java.rasbetbackend.payload.request.BetRequest;
import net.java.rasbetbackend.payload.request.IntRequest;
import net.java.rasbetbackend.payload.request.SignupRequest;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bets")

public class BetController {

    @Autowired
    BetRepository betRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameRepository gameRepository;

    @GetMapping(value = "/userbets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> getUserBets(Authentication authentication) {
        int nif = (int) userRepository.findByUsername(authentication.getName()).get().getNif();
        List<Optional<Bet>> betList = betRepository.findAllByNif(nif);
        List<JSONObject> objects = new ArrayList<>();
        for (Optional<Bet> betOptional : betList) {
            if(betOptional.isPresent()) {
                Bet bet = betOptional.get();
                JSONObject obj = new JSONObject();
                obj.put("Value", bet.getValue());
                obj.put("date", bet.getDateTime());
                obj.put("odd", bet.getOdd());
                obj.put("state", bet.getState());
                objects.add(obj);
            }

        }
        return ResponseEntity.ok(new MessageResponse(objects.toString()));
    }

    @GetMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> cancelBet(@RequestBody IntRequest idBet, Authentication authentication) {
        int nif = (int) userRepository.findByUsername(authentication.getName()).get().getNif();
        if(!betRepository.existsByNifAndIdBet(nif, idBet.getId())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: You don not won that bet or it does not exist!"));
        }
        Bet bet = betRepository.findByIdBet(idBet.getId()).get();
        Game game = gameRepository.findByIdGame(bet.getIdGame()).get();
        if(bet.getDateTime().isBefore(game.getDateTime()) &&
            LocalDateTime.now().isBefore(game.getDateTime()) &&
            bet.getDateTime().plusMinutes(20).isAfter(LocalDateTime.now())) {
            walletRepository.findByNif(nif).get().setBudget(walletRepository.findByNif(nif).get().getBudget() + bet.getValue());
            betRepository.deleteByIdBet(idBet.getId());
            return ResponseEntity.ok(new MessageResponse("Bet Deleted successfully"));
        }
        else return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: You can not cancel that bet now!"));
    }

    @GetMapping(value = "/bet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> registerBet(@RequestBody BetRequest betRequest, Authentication authentication) {
        int nif = (int) userRepository.findByUsername(authentication.getName()).get().getNif();

        if(betRepository.existsByNifAndIdGame(nif, betRequest.getIdGame())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: You already placed a bet in this game!"));
        }

        if(!gameRepository.existsByIdGame(betRequest.getIdGame())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Game does not exist!"));
        }

        Wallet wallet = null;
        if(walletRepository.existsByNif(nif)){
            Optional<Wallet> walletOptional = walletRepository.findByNif(nif);
            wallet = walletOptional.get();
        }
        else {
            wallet = new Wallet(nif, 0.0);
            walletRepository.save(wallet);
        }

        if(wallet.getBudget() - betRequest.getValue() < 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: invalid bet: no budget!"));
        }

        wallet.setBudget(wallet.getBudget()-betRequest.getValue());

        walletRepository.saveAndFlush(wallet);


        Bet bet = new Bet(nif,
                betRequest.getValue(),
                BetState.Done,
                betRequest.getOdd(),
                betRequest.getIdGame(),
                betRequest.getType());

        betRepository.save(bet);

        return ResponseEntity.ok(new MessageResponse("Bet created successfully!"));
    }


}