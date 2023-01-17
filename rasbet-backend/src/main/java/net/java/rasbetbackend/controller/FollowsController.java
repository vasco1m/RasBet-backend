package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.model.Follow;
import net.java.rasbetbackend.model.Role;
import net.java.rasbetbackend.model.User;
import net.java.rasbetbackend.model.UserType;
import net.java.rasbetbackend.payload.request.FollowRequest;
import net.java.rasbetbackend.payload.request.IntRequest;
import net.java.rasbetbackend.payload.request.RoleRequest;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.FollowRepository;
import net.java.rasbetbackend.repository.GameRepository;
import net.java.rasbetbackend.repository.RoleRepository;
import net.java.rasbetbackend.repository.UserRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/follows")
public class FollowsController {

    @Autowired
    FollowRepository followRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;


    @PostMapping(value = "/follow")
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> follow(@Valid @RequestBody FollowRequest followRequest, Authentication authentication) {
        int nif = (int) userRepository.findByUsername(authentication.getName()).get().getNif();
        if (followRepository.existsByNifAndIdGame(nif, followRequest.getIdGame())) {
            return  ResponseEntity.badRequest().body(new MessageResponse("Already following"));
        }
        if(!gameRepository.existsByIdGame(followRequest.getIdGame())){
            return  ResponseEntity.badRequest().body(new MessageResponse("Game not found."));
        }
        Follow follow = new Follow(nif, followRequest.getIdGame());
        followRepository.save(follow);
        return  ResponseEntity.ok(new MessageResponse("Followed successfully!"));
    }


    @PostMapping(value = "/unfollow")
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> unfollow(@Valid @RequestBody FollowRequest followRequest, Authentication authentication) {
        int nif = (int) userRepository.findByUsername(authentication.getName()).get().getNif();
        if (!followRepository.existsByNifAndIdGame(nif, followRequest.getIdGame())) {
            return  ResponseEntity.badRequest().body(new MessageResponse("You were not following that game"));
        }
        Optional<Follow> follow = followRepository.findByNifAndIdGame(nif, followRequest.getIdGame());
        if (follow.isPresent()) followRepository.delete(follow.get());
        return  ResponseEntity.ok(new MessageResponse("Unfollowed successfully!"));
    }

    @GetMapping(value = "/followsNif")
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> followsByNif(@Valid @RequestBody IntRequest nif) {
        if (!followRepository.existsByNif(nif.getId())) {
            return  ResponseEntity.badRequest().body(new MessageResponse("Not following anyone"));
        }
        JSONObject json = new JSONObject();
        List<Integer> follows = new ArrayList();
        List<Optional<Follow>> follow = followRepository.findAllByNif(nif.getId());
        for (Optional<Follow> f : follow) {
            if (f.isPresent()) follows.add(f.get().getIdGame());
        }
        json.put("follows", follows);
        return  ResponseEntity.ok(json.toString());
    }

    @GetMapping(value = "/followsGame")
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> followsByGame(@Valid @RequestBody IntRequest idGame) {
        if (!followRepository.existsByIdGame(idGame.getId())) {
            return  ResponseEntity.badRequest().body(new MessageResponse("Game is not being followed."));
        }
        JSONObject json = new JSONObject();
        List<Integer> follows = new ArrayList();
        List<Optional<Follow>> follow = followRepository.findAllByIdGame(idGame.getId());
        for (Optional<Follow> f : follow) {
            if (f.isPresent()) follows.add(f.get().getNif());
        }
        json.put("follows", follows);
        return  ResponseEntity.ok(json.toString());
    }
}