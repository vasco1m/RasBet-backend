package net.java.rasbetbackend.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import net.java.rasbetbackend.exception.AgeException;
import net.java.rasbetbackend.payload.request.ChangeUserDataRequest;
import net.java.rasbetbackend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import net.java.rasbetbackend.model.UserType;
import net.java.rasbetbackend.model.Role;
import net.java.rasbetbackend.model.User;
import net.java.rasbetbackend.payload.request.LoginRequest;
import net.java.rasbetbackend.payload.request.SignupRequest;
import net.java.rasbetbackend.payload.response.JwtResponse;
import net.java.rasbetbackend.payload.response.MessageResponse;
import net.java.rasbetbackend.repository.RoleRepository;
import net.java.rasbetbackend.repository.UserRepository;
import net.java.rasbetbackend.security.jwt.JwtUtils;
import net.java.rasbetbackend.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                (long) userDetails.getNif(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws Exception {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        try {
            User user = new User(signUpRequest.getNif(),
                    signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()),
                    signUpRequest.getBornDate());
            Set<String> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet<>();
            if (strRoles == null) {
                Role userRole = roleRepository.findByName(UserType.ROLE_BETTER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
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
                });
            }
            user.setRoles(roles);
            userRepository.save(user);
        }
        catch (Exception e){
            if(e.getClass() == AgeException.class)
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: You should be at least 18 years old."));
            else return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private boolean validCode(String code){
        if(LocalDateTime.ofEpochSecond(Long.parseLong(code), 0, ZoneOffset.MAX).isAfter(LocalDateTime.now())) return true;
        return false;
    }

    private String createCode(){
        Long seconds = LocalDateTime.now().plusMinutes(5).toEpochSecond(ZoneOffset.MAX);
        return seconds.toString();
    }

    @PostMapping("/changeData/confirm")
    public ResponseEntity<?> changeUserDataConfirmed(@Valid @RequestBody ChangeUserDataRequest changeUserDataRequest, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        if(validCode(changeUserDataRequest.getCode())) {
            if (changeUserDataRequest.getUsername() != "" && changeUserDataRequest.getUsername() != null) user.setUsername(changeUserDataRequest.getUsername());
            if (changeUserDataRequest.getEmail() != "" && changeUserDataRequest.getEmail() != null) user.setEmail(changeUserDataRequest.getEmail());
            if (changeUserDataRequest.getPassword() != "" && changeUserDataRequest.getPassword() != null)
                user.setPassword(encoder.encode(changeUserDataRequest.getPassword()));
            userRepository.saveAndFlush(user);
            return ResponseEntity.ok(new MessageResponse("User data changed successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Code provided is invalid."));
    }

    @GetMapping("/changeData")
    public ResponseEntity<?> changeUserData(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        try {
            emailService.sendMail(user.getEmail(), "Change user data", "Your code is: " + createCode());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error: An error occurred.");
        }
        return ResponseEntity.ok(new MessageResponse("Code sent successfully."));
    }
}