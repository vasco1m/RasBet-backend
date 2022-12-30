package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.model.Wallet;
import net.java.rasbetbackend.payload.request.WalletRequest;
import net.java.rasbetbackend.payload.request.IntRequest;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.UserRepository;
import net.java.rasbetbackend.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/wallet")

public class WalletController {


    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> addFounds(@RequestBody WalletRequest walletRequest, Authentication authentication) {

        Wallet wallet;
        if(walletRepository.existsByNif((int) userRepository.findByUsername(authentication.getName()).get().getNif())){
            Optional<Wallet> walletOptional = walletRepository.findByNif((int) userRepository.findByUsername(authentication.getName()).get().getNif());
            wallet = walletOptional.get();
        }
        else {
            wallet = new Wallet((int) userRepository.findByUsername(authentication.getName()).get().getNif(), 0.0);
            walletRepository.save(wallet);
        }

        wallet.setBudget(wallet.getBudget() + walletRequest.getValue());

        walletRepository.saveAndFlush(wallet);

        return ResponseEntity.ok(new MessageResponse("Money added successfully!"));
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> getFounds(@RequestBody WalletRequest walletRequest, Authentication authentication) {

        Wallet wallet;
        if(walletRepository.existsByNif((int) userRepository.findByUsername(authentication.getName()).get().getNif())){
            Optional<Wallet> walletOptional = walletRepository.findByNif((int) userRepository.findByUsername(authentication.getName()).get().getNif());
            wallet = walletOptional.get();
        }
        else {
            wallet = new Wallet((int) userRepository.findByUsername(authentication.getName()).get().getNif(), 0.0);
            walletRepository.save(wallet);
        }

        if(wallet.getBudget() - walletRequest.getValue() < 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: invalid bet: not enough founds!"));
        }
        wallet.setBudget(wallet.getBudget() - walletRequest.getValue());

        walletRepository.saveAndFlush(wallet);

        return ResponseEntity.ok(new MessageResponse("Money added successfully!"));
    }

    @PostMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> getBalance(@RequestBody Authentication authentication) {
        Wallet wallet;
        if(walletRepository.existsByNif((int) userRepository.findByUsername(authentication.getName()).get().getNif())){
            Optional<Wallet> walletOptional = walletRepository.findByNif((int) userRepository.findByUsername(authentication.getName()).get().getNif());
            wallet = walletOptional.get();
        }
        else{
            wallet = new Wallet((int) userRepository.findByUsername(authentication.getName()).get().getNif(), 0.0);
            walletRepository.save(wallet);
        }

        return ResponseEntity.ok(wallet.getBudget());
    }

    @PostMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> getTransactions(@RequestBody Authentication authentication) {

        return ResponseEntity.badRequest().body("Error: An error occurred.");
    }

}