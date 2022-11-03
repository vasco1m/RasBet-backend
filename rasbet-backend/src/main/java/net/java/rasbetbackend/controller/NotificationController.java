package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.model.Notification;
import net.java.rasbetbackend.model.User;
import net.java.rasbetbackend.payload.request.NotificationRequest;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.NotificationRepository;
import net.java.rasbetbackend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;
    
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BETTER')")
    public ResponseEntity<?> getNotifications(Authentication authentication) {
        List<JSONObject> objects = new ArrayList<>();
        for (Optional<Notification> n : notificationRepository.findAllByNif((int) userRepository.findByUsername(authentication.getName()).get().getNif())) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", n.get().getTitle());
            jsonObject.put("date", n.get().getDate());
            jsonObject.put("description", n.get().getDescription());
            objects.add(jsonObject);
        }
        return ResponseEntity.ok(objects);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createNotification(@Valid @RequestBody NotificationRequest notificationRequest){
        boolean all = false;
        if(notificationRequest.getNif() == 999999999) all = true; //notification will be sent to everyone
        else if (!userRepository.existsByNif(notificationRequest.getNif())){return ResponseEntity.badRequest().body(new MessageResponse("Error: NIF Notification Incorrect!"));}
        try{
            if(all){
                for (User u : userRepository.findAll()) {
                    Notification notification = new Notification(
                            (int) u.getNif(),
                            notificationRequest.getTitle(),
                            notificationRequest.getDescription()
                    );
                    notificationRepository.save(notification);
                }
            }
            else {
                Notification notification = new Notification(
                        notificationRequest.getNif(),
                        notificationRequest.getTitle(),
                        notificationRequest.getDescription()
                );
                notificationRepository.save(notification);
            }
        } catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        return ResponseEntity.ok(new MessageResponse("Notification(s) sent successfully!"));
    }



}
