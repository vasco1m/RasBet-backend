package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.model.Game;
import net.java.rasbetbackend.model.Sale;
import net.java.rasbetbackend.model.SaleState;
import net.java.rasbetbackend.payload.request.SalesRequest;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.GameRepository;
import net.java.rasbetbackend.repository.SaleRepository;
import net.java.rasbetbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    SaleRepository saleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GameRepository gamesRepository;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createSale(@Valid @RequestBody SalesRequest salesRequest){
        boolean all_games = false;
        if (salesRequest.getIdGame() == -1){
            all_games = true;
        }
        else if(!gamesRepository.existsByIdGame(salesRequest.getIdGame())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Game Non-Existing!"));
        }
        try{
            if(all_games){
                for (Game game: gamesRepository.findAll()){
                    Sale sale = new Sale(
                            game.getIdGame(),
                            salesRequest.getValidationTime(),
                            SaleState.Available
                    );
                    saleRepository.save(sale);
                }
            }
            else{
                Sale sale = new Sale(
                        salesRequest.getIdGame(),
                        salesRequest.getValidationTime(),
                        SaleState.Available
                );
                saleRepository.save(sale);
            }
        } catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        return ResponseEntity.ok(new MessageResponse("Sale created successfully!"));
    }


}
