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
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

@EnableAsync
@EnableScheduling
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
                    obj.put("draw", game.isDraw());
                    if(game.isType() == true){
                        Optional<GameOneToMany> g = gameOneToManyRepository.findByIdGame(game.getIdGame());
                        obj.put("name", g.get().getName());
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

    @Scheduled(fixedRate = 100000)
    public ResponseEntity<?> updateGames(){
        try {
            URL url = new URL("http://ucras.di.uminho.pt/v1/games");
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() != 200){
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
                if (!categoryRepository.existsByName("Football")){categoryRepository.save(new Category("Football"));}
                for (int i = 0; i<dataObject.size(); i++){
                    JSONObject game = (JSONObject) dataObject.get(i);
                    if (!gameRepository.existsByApiID((String) game.get("id"))){
                        String date = (game.get("commenceTime").toString());
                        Game g = new Game((String) game.get("id"), false, LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")), 0);
                        gameRepository.save(g);
                        GameOneToOne gotO = new GameOneToOne(g.getIdGame(),(String) game.get("homeTeam"),(String) game.get("awayTeam"));
                        gameOneToOneRepository.save(gotO);
                    }
                    Optional<Game> game_existing = gameRepository.findByApiID((String) game.get("id"));
                    if (game_existing.isPresent() && ( ((Boolean) game.get("completed")) == true )){
                        Game game_exists = game_existing.get();
                        //update existing game
                        String result = (String) game.get("scores");
                        String[] goals = result.split("x",2);
                        Integer[] golinhos = new Integer[2];
                        golinhos[0] = Integer.parseInt(goals[0]);
                        golinhos[1] = Integer.parseInt(goals[1]);
                        if(golinhos[0] > golinhos[1] ){game_exists.setDraw(false); game_exists.setResult(0);}
                        else if(golinhos[0] == golinhos[1]){game_exists.setDraw(true); game_exists.setResult(-1);}
                        else {game_exists.setDraw(false); game_exists.setResult(1);}
                        gameRepository.saveAndFlush(game_exists);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("API: Games Updated !"));
    }
}