package com.example.todolist.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        Optional<User> userOptional = userService.findByUsername(loginRequest.getUsername());
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(loginRequest.getPassword())) {
            String token = JwtTokenUtil.generateToken(new UserDetailsImpl(userOptional.get()));
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public void myName() {
        System.out.println("Hari krishna");
    }
}

