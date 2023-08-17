package users.app.rest.controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import users.config.JwtTokenUtil;
import users.model.User;
import users.repository.UserRepository;
import users.respones.AuthResponse;
import users.respones.LoginRequest;
import users.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/auth")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticate(loginRequest.getUsername(), loginRequest.getPassword());

            final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @PostMapping("/userLogout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // Perform any necessary logout logic

        // Clear the authentication
        SecurityContextHolder.clearContext();

        // Invalidate the current session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/api/currentUser")
    public User currentUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = userRepository.findByUsername(username);

        return currentUser;
    }


/*
    @PostMapping("/registrate")
    public String registrateUser(@Valid User user) throws AppException {
        User userFromDatabase = userRepository.findByUsername(user.getUsername());
        if(userFromDatabase != null){
            throw new AppException(AppError.BAD_REQUEST);
        }
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setCreatedDate(Instant.now());
        userRepository.save(user);

        throw new AppException(AppError.STUDENT_NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.getPassword());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, null)
                .body(new UserInfo(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }*/
}
