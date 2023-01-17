package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.model.*;
import net.java.rasbetbackend.payload.request.BetRequest;
import net.java.rasbetbackend.payload.request.ChangeBetStateRequest;
import net.java.rasbetbackend.payload.request.IntRequest;
import net.java.rasbetbackend.payload.request.MultipleBetRequest;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    MultipleBetRepository multipleBetRepository;

    @Autowired
    MultipleBetLineRepository multipleBetLineRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    FollowRepository followRepository;
    
    @PostMapping("/change_state")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> changeState(@Valid @RequestBody ChangeBetStateRequest cbsr){
        if(!betRepository.existsByIdBet(cbsr.getIdBet())){ return ResponseEntity.badRequest().body(new MessageResponse("Error: Bet Non-Existing!")); }
        try{
            Optional<Bet> bet = betRepository.findByIdBet(cbsr.getIdBet());
            if(bet.isPresent()){
                Bet be=bet.get();
                be.setState(BetState.valueOf(cbsr.getState()));
                betRepository.saveAndFlush(be);
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        return ResponseEntity.ok(new MessageResponse("Bet State changed successfully!"));
    }

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
                obj.put("value", bet.getValue());
                obj.put("date", bet.getDateTime());
                obj.put("odd", bet.getOdd());
                obj.put("state", bet.getState());
                obj.put("type", bet.getType());
                obj.put("idGame", bet.getIdGame());
                objects.add(obj);
            }

        }
        return ResponseEntity.ok(new MessageResponse(objects.toString()));
    }

    @GetMapping(value = "/usermultiplebets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> getUserMultipleBets(Authentication authentication) {
        int nif = (int) userRepository.findByUsername(authentication.getName()).get().getNif();
        List<Optional<MultipleBet>> betList = multipleBetRepository.findAllByNif(nif);
        List<JSONObject> objects = new ArrayList<>();
        for (Optional<MultipleBet> multipleBetOptional : betList) {
            if(multipleBetOptional.isPresent()) {
                JSONObject obj = new JSONObject();
                obj.put("date", multipleBetOptional.get().getDateTime());
                obj.put("state", multipleBetOptional.get().getState());
                obj.put("value", multipleBetOptional.get().getValue());
                List<Optional<MultipleBetLine>> bets = multipleBetLineRepository.findAllByIdBet(multipleBetOptional.get().getIdBet());
                for (Optional<MultipleBetLine> betOptional : bets) {
                    MultipleBetLine bet = betOptional.get();
                    JSONObject sBet = new JSONObject();
                    sBet.put("idMultipleBet", bet.getId());
                    sBet.put("odd", bet.getOdd());
                    sBet.put("idGame", bet.getIdGame());
                    sBet.put("type", bet.getType());
                    obj.put("bets", sBet);
                }
                objects.add(obj);
            }

        }
        return ResponseEntity.ok(new MessageResponse(objects.toString()));
    }

    @PostMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> cancelBet(@RequestBody IntRequest idBet, Authentication authentication) {
        int nif = (int) userRepository.findByUsername(authentication.getName()).get().getNif();
        if(!betRepository.existsByNifAndIdBet(nif, idBet.getId())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: You don not won that bet or it does not exist!"));
        }
        Bet bet = betRepository.findByIdBet(idBet.getId()).get();
        if(bet.getState().equals(BetState.Canceled)) return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: That bet is already canceled!"));
        Game game = gameRepository.findByIdGame(bet.getIdGame()).get();
        if(bet.getDateTime().isBefore(game.getDateTime()) &&
            LocalDateTime.now().isBefore(game.getDateTime()) &&
            bet.getDateTime().plusMinutes(20).isAfter(LocalDateTime.now())) {
            Transaction transaction = new Transaction(bet.getNif(), TransactionType.BetCancellation, bet.getValue());
            walletRepository.findByNif(nif).get().setBudget(walletRepository.findByNif(nif).get().getBudget() + bet.getValue());
            bet.setState(BetState.Canceled);
            betRepository.saveAndFlush(bet);
            transactionRepository.saveAndFlush(transaction);
            followRepository.deleteByNifAndIdGame(nif, game.getIdGame());
            return ResponseEntity.ok(new MessageResponse("Bet Deleted successfully"));
        }
        else return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: You can not cancel that bet now!"));
    }

    @PostMapping(value = "/cancelmultiple", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> cancelMultipleBet(@RequestBody IntRequest idBet, Authentication authentication) {
        int nif = (int) userRepository.findByUsername(authentication.getName()).get().getNif();
        if(!multipleBetRepository.existsByNifAndIdBet(nif, idBet.getId())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: You don not won that bet or it does not exist!"));
        }
        MultipleBet bet = multipleBetRepository.findByIdBet(idBet.getId()).get();
        if(bet.getState().equals(BetState.Canceled)) return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: That bet is already canceled!"));
        List<Optional<MultipleBetLine>> bets = multipleBetLineRepository.findAllByIdBet(bet.getIdBet());
        for (Optional<MultipleBetLine> sBet : bets) {
            Game game = gameRepository.findByIdGame(sBet.get().getIdGame()).get();
            if(bet.getDateTime().isBefore(game.getDateTime()) &&
                    LocalDateTime.now().isBefore(game.getDateTime()) &&
                    bet.getDateTime().plusMinutes(20).isAfter(LocalDateTime.now())) {
                Optional<Follow> follow = followRepository.findByNifAndIdGame(nif, game.getIdGame());
                if (follow.isPresent()) followRepository.delete(follow.get());
            }
            else return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: You can not cancel that bet now!"));
        }
        walletRepository.findByNif(nif).get().setBudget(walletRepository.findByNif(nif).get().getBudget() + bet.getValue());
        Transaction transaction = new Transaction(bet.getNif(), TransactionType.BetCancellation, bet.getValue());
        bet.setState(BetState.Canceled);
        multipleBetRepository.saveAndFlush(bet);
        transactionRepository.saveAndFlush(transaction);
        return ResponseEntity.ok(new MessageResponse("Multiple bet deleted successfully!"));
    }

    @PostMapping(value = "/bet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> registerBet(@RequestBody BetRequest betRequest, Authentication authentication) {
        int nif = (int) userRepository.findByUsername(authentication.getName()).get().getNif();

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

        Transaction transaction = new Transaction(nif, TransactionType.PlaceBet, betRequest.getValue());
        transactionRepository.saveAndFlush(transaction);

        Bet bet = new Bet(nif,
                betRequest.getValue(),
                BetState.Done,
                betRequest.getOdd(),
                betRequest.getIdGame(),
                betRequest.getType());

        betRepository.save(bet);
        Optional<Follow> follow = followRepository.findByNifAndIdGame(nif, betRequest.getIdGame());
        if (!follow.isPresent()) {
            Follow f = new Follow(nif, betRequest.getIdGame());
            followRepository.saveAndFlush(f);
        }

        return ResponseEntity.ok(new MessageResponse("Bet created successfully!"));
    }

    @PostMapping(value = "/multiplebet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> registerMultipleBet(@RequestBody MultipleBetRequest multipleBetRequest, Authentication authentication) {
        int nif = (int) userRepository.findByUsername(authentication.getName()).get().getNif();

        MultipleBet multipleBet = new MultipleBet(nif, multipleBetRequest.getValue(), BetState.Done);
        multipleBetRepository.saveAndFlush(multipleBet);

        multipleBet = multipleBetRepository.findByNifAndDateTimeBetween(nif, multipleBet.getDateTime().minusMinutes(1), multipleBet.getDateTime().plusMinutes(1)).get();

        Wallet wallet = null;
        if (walletRepository.existsByNif(nif)) {
            Optional<Wallet> walletOptional = walletRepository.findByNif(nif);
            wallet = walletOptional.get();
        } else {
            wallet = new Wallet(nif, 0.0);
            walletRepository.save(wallet);
        }

        if (wallet.getBudget() - multipleBetRequest.getValue() < 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: invalid bet: no budget!"));
        }

        wallet.setBudget(wallet.getBudget() - multipleBetRequest.getValue());
        walletRepository.saveAndFlush(wallet);

        Transaction transaction = new Transaction(nif, TransactionType.PlaceBet, multipleBetRequest.getValue());
        transactionRepository.saveAndFlush(transaction);

        for(BetRequest betRequest : multipleBetRequest.getBetRequests()) {
            if (!gameRepository.existsByIdGame(betRequest.getIdGame())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Game does not exist!"));
            }

            MultipleBetLine bet = new MultipleBetLine(
                    multipleBet.getIdBet(),
                    betRequest.getOdd(),
                    betRequest.getIdGame());

            multipleBetLineRepository.saveAndFlush(bet);

            Optional<Follow> follow = followRepository.findByNifAndIdGame(nif, betRequest.getIdGame());
            if (!follow.isPresent()) {
                Follow f = new Follow(nif, betRequest.getIdGame());
                followRepository.saveAndFlush(f);
            }
        }
        return ResponseEntity.ok(new MessageResponse("Bet created successfully!"));
    }

    @GetMapping(value = "/allbets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> getAllBets(@RequestBody Authentication authentication){
        if(userRepository.existsByUsername(authentication.getName())){
            User user = userRepository.findByUsername(authentication.getName()).get();
            List<JSONObject> objects = new ArrayList<>();
            for(Optional<Bet> oBet: betRepository.findAllByNif((int) user.getNif())){
                if (oBet.isPresent()){
                    Bet b = oBet.get();
                    JSONObject obj = new JSONObject();
                    obj.put("idBet", b.getIdBet());
                    obj.put("nif", b.getNif());
                    obj.put("value", b.getValue());
                    obj.put("dateTime", b.getDateTime());
                    obj.put("state", b.getState());
                    obj.put("odd", b.getOdd());
                    obj.put("idGame", b.getIdGame());
                    obj.put("type", b.getType());//TODO nao me lembro se é este type ou o do Game
                    objects.add(obj);
                }
            }
            return ResponseEntity.ok(new MessageResponse(objects.toString()));
        }
        else return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Invalid User!"));
    }


}
