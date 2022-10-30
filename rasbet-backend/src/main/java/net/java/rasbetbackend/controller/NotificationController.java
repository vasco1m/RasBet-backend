package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.model.Notification;
import net.java.rasbetbackend.payload.request.NotificationRequest;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.NotificationRepository;
import net.java.rasbetbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/noti")
public class NotificationController {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createNotification(@Valid @RequestBody NotificationRequest notificationRequest){
        if (notificationRepository.existsByIdNotification(notificationRequest.getId())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: ID Notification Incorrect!"));
            //ver se isto pode existir
        }
        //if (!userRepository.existsByNif(notificationRequest.getNif())){return ResponseEntity.badRequest().body(new MessageResponse("Error: NIF Notification Incorrect!"));}
        try{
            Notification notification = new Notification(
                    notificationRequest.getId(),
                    notificationRequest.getNif(),
                    notificationRequest.getDate(),//LocalDateTime.now()
                    notificationRequest.getTitle(),
                    notificationRequest.getDescription()
            );


            notificationRepository.save(notification);

            //Como notificar o cliente da notificaçao ????

        } catch(Exception e){

        }

        return ResponseEntity.ok(new MessageResponse("Notification sent successfully!"));
    }



}
