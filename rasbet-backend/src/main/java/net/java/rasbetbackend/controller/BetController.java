package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.model.Bet;
import net.java.rasbetbackend.model.BetState;
import net.java.rasbetbackend.payload.request.ChangeBetStateRequest;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bets")
public class BetController {

    @Autowired
    BetRepository betRepository;

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

}
