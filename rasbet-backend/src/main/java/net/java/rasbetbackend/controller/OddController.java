package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.model.*;
import net.java.rasbetbackend.payload.request.OddRequest;
        import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.security.access.prepost.PreAuthorize;
        import org.springframework.web.bind.annotation.*;

        import javax.validation.Valid;
        import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/oddSpecialist")
public class OddController {
    @Autowired
    OddRepository oddRepository;
    @Autowired
    GameRepository gameRepository;

    @Autowired
    FollowRepository followRepository;

    @Autowired
    GameOneToManyRepository gameOneToManyRepository;

    @Autowired
    GameOneToOneRepository gameOneToOneRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('ROLE_SPECIALIST') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> insertOdd(@Valid @RequestBody OddRequest oddRequest){
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
            Game game_exists = gameRepository.findByIdGame(oddRequest.getIdGame()).get();
            GameOneToMany gameOneToMany;
            GameOneToOne gameOneToOne;
            if (game_exists.isType()) {
                gameOneToMany = gameOneToManyRepository.findByIdGame(game_exists.getIdGame()).get();
                gameOneToOne = null;
            }
            else {
                gameOneToOne = gameOneToOneRepository.findByIdGame(game_exists.getIdGame()).get();
                gameOneToMany = null;
            }
            List<Optional<Follow>> followers = followRepository.findAllByIdGame(game_exists.getIdGame());
            for (Optional<Follow> follower : followers) {
                if (follower.isPresent()){
                    Notification notification;
                    if (!game_exists.isType()) {
                        notification = new Notification(follower.get().getNif(), "Odd Alterada", "Uma odd do " + gameOneToOne.getTpA() + " vs " + gameOneToOne.getTpB() + " foi alterada!");
                    }
                    else notification = new Notification(follower.get().getNif(), "Odd Alterada", "Uma odd jogo " + gameOneToMany.getName() + " foi alterada!");
                    notificationRepository.saveAndFlush(notification);
                }
            }
            oddRepository.save(odd);
        } catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        return ResponseEntity.ok(new MessageResponse("Odd added successfully!"));
    }



}