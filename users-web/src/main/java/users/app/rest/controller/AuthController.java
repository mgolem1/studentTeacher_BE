package users.app.rest.controller;

import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import users.config.JwtTokenUtil;
import users.model.User;
import users.repository.UserRepository;
import users.respones.AuthResponse;
import users.respones.LoginRequest;
import users.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("")
    public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {
        System.out.println("login"+loginRequest.getPassword()+loginRequest.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails=userService.loadUserByUsername(loginRequest.getUsername());

        System.out.println(userDetails.getUsername());

        User user=userRepository.findByUsername(userDetails.getUsername());

        String jwt=jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt,user));
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
