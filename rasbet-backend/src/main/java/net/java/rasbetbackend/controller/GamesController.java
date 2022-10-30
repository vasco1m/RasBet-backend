package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.model.*;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/games")
public class GamesController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameOneToOneRepository gameOneToOneRepository;

    @Autowired
    GameOneToManyRepository gameOneToManyRepository;

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    OddRepository oddRepository;

    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER') or hasRole('ROLE_SPECIALIST') or hasRole('ROLE_ADMIN')")
    public String getCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<String> cat = new ArrayList<>();
        for (Category category : categories) {
            cat.add(category.getName());
        }
        return cat.toString();
    }

    @GetMapping(value = "/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER') or hasRole('ROLE_SPECIALIST') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getGame(@PathVariable("category") String category) {
        if(categoryRepository.existsByName(category)) {
            Optional<Category> cat = categoryRepository.findByName(category);
            List<Game> games = gameRepository.findAll();
            List<JSONObject> objects = new ArrayList<>();
            for (Game game : games){
                if (game.getIdCategory() == cat.get().getIdCategory()){
                    JSONObject obj = new JSONObject();
                    obj.put("id", game.getIdGame());
                    obj.put("idCategory", game.getIdCategory());
                    if(game.isType() == true){
                        Optional<GameOneToMany> g = gameOneToManyRepository.findByIdGame(game.getIdGame());
                        obj.put("date", g.get().getDateTime());
                        obj.put("draw", g.get().isDraw());
                        List<Participant> participants = participantRepository.findAll();
                        Map<Integer,String> p = new HashMap<>();
                        for (Participant participant : participants) {
                            if(participant.getIdGame() == game.getIdGame()){
                                p.put(participant.getIdParticipant(),participant.getName());
                            }
                        }
                        obj.put("participants", p);
                    }
                    else{
                        Optional<GameOneToOne> g = gameOneToOneRepository.findByIdGame(game.getIdGame());
                        obj.put("date", g.get().getDate());
                        obj.put("home",g.get().getTpA());
                        obj.put("away",g.get().getTpB());
                    }
                    List<Odd> odds = oddRepository.findAll();
                    Map<Integer,Double> o = new HashMap<>();
                    for (Odd odd : odds) {
                        if(odd.getIdGame() == game.getIdGame()){
                            o.put(odd.getType(),odd.getValue());
                        }
                    }
                    obj.put("odds", o);
                    objects.add(obj);
                }
            }
            return ResponseEntity.ok(new MessageResponse(objects.toString()));
        }
        else return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Unknown category!"));
    }
}