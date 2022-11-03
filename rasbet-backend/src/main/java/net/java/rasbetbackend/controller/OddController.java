package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.model.Odd;
        import net.java.rasbetbackend.payload.request.OddRequest;
        import net.java.rasbetbackend.payload.response.MessageResponse;
        import net.java.rasbetbackend.repository.GameRepository;
        import net.java.rasbetbackend.repository.OddRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.security.access.prepost.PreAuthorize;
        import org.springframework.web.bind.annotation.*;

        import javax.validation.Valid;
        import java.time.LocalDateTime;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/oddSpecialist")
public class OddController {
    @Autowired
    OddRepository oddRepository;
    @Autowired
    GameRepository gameRepository;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('ROLE_SPECIALIST')")
    public ResponseEntity<?> createNotification(@Valid @RequestBody OddRequest oddRequest){
        boolean all = false;
        if (!gameRepository.existsByIdGame(oddRequest.getIdGame())){return ResponseEntity.badRequest().body(new MessageResponse("Error: Game ID Incorrect!"));}
        try{
            Odd odd = new Odd(
                    oddRequest.getIdGame(),
                    "SPECIALIST: " + oddRequest.getIdSpecialist(),
                    oddRequest.getValue(),
                    oddRequest.getType(),
                    LocalDateTime.now()
            );
            oddRepository.save(odd);
        } catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        return ResponseEntity.ok(new MessageResponse("Odd added successfully!"));
    }



}