package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.model.*;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
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
                    obj.put("date", game.getDateTime());
                    if(game.isType() == true){
                        Optional<GameOneToMany> g = gameOneToManyRepository.findByIdGame(game.getIdGame());
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

    public ResponseEntity<?> updateGames(){
        try {
            URL url = new URL("http://ucras.di.uminho.pt/v1/games");
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() != 200){
                System.out.println("Error: Error Connecting to API!");
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Error Connecting to API!"));
            } else{
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while(scanner.hasNext()){
                    informationString.append(scanner.nextLine());
                }
                scanner.close();
                JSONParser parse = new JSONParser();
                JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));
                for (Object obj : dataObject){
                    JSONObject game = (JSONObject) obj;
                    System.out.println(game);
                    if(gameRepository.existsByApiID((String) game.get("id"))){
                        //TODO MAKE CHANGES WHEN UPDATED
                    }
                    else{
                        Game g = new Game((String) game.get("id"), (LocalDateTime) game.get("commenceTime"),false, 0);
                        gameRepository.save(g);
                        GameOneToOne gotO = new GameOneToOne(g.getIdGame(),(String) game.get("homeTeam"),(String) game.get("awayTeam"));
                        gameOneToOneRepository.save(gotO);
                    }
                }
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("API: Games Updated !"));
    }
}