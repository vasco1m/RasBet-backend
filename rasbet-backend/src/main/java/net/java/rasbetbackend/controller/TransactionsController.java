package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.model.*;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.TransactionRepository;
import net.java.rasbetbackend.repository.UserRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/get")
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> getTransactionsUser(@Valid @RequestBody Authentication authentication){
        List<Optional<Transaction>> transactions = transactionRepository.findAllByNif((int) userRepository.findByUsername(authentication.getName()).get().getNif());
        JSONObject transactionsJSON = new JSONObject();
       for (Optional<Transaction> t : transactions) {
            if (t.isPresent()) {
                Transaction transaction = t.get();
                JSONObject tr = new JSONObject();
                tr.put("date", transaction.getDate());
                tr.put("type", transaction.getType());
                tr.put("value", transaction.getValue());
                transactionsJSON.put(transaction.getIdTransaction(), tr);
            }
        }
        return ResponseEntity.ok(transactionsJSON.toString());
    }


}
