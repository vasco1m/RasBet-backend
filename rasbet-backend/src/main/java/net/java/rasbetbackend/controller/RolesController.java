package net.java.rasbetbackend.controller;

import net.java.rasbetbackend.model.*;
import net.java.rasbetbackend.payload.request.RoleRequest;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/change")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> setRole(@Valid @RequestBody RoleRequest roleRequest){
        if (!userRepository.existsByNif(roleRequest.getNif())){return ResponseEntity.badRequest().body(new MessageResponse("Error: NIF Incorrect!"));}
        Set<Role> roles = new HashSet<>();
        switch (roleRequest.getRole()) {
            case "ROLE_ADMIN":
                Role adminRole = roleRepository.findByName(UserType.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);

                break;
            case "ROLE_SPECIALIST":
                Role modRole = roleRepository.findByName(UserType.ROLE_SPECIALIST)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);

                break;
            default:
                Role userRole = roleRepository.findByName(UserType.ROLE_BETTER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
        }
        User user = userRepository.findByNif(roleRequest.getNif()).get();
        user.setRoles(roles);
        userRepository.saveAndFlush(user);
        return ResponseEntity.ok(new MessageResponse("Role changed successfully!"));
    }

}