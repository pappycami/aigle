package com.ainapapy.aigle.security;

import com.ainapapy.aigle.models.User;
import com.ainapapy.aigle.models.convertors.UserConvertor;
import com.ainapapy.aigle.models.dto.UserDTO;
import com.ainapapy.aigle.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private UserConvertor userConvertor;
    
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO dto) throws Exception {
        
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.ok(Map.of(
                "status", "ERROR",
                "message", "Utilisateur déja existant"
            ));
        }
        
        User user = userConvertor.convertToEntity(dto);
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(User.roles.USER);
        userRepository.save(user);
                
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.getEmail(), dto.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = tokenProvider.generateToken(auth);

        return ResponseEntity.ok(Map.of(
            "accessToken", token,
            "tokenType", "Bearer"
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new Exception("Email invalide"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new Exception("Mot de passe invalide");
        }
        
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = tokenProvider.generateToken(auth);

        return ResponseEntity.ok(Map.of(
            "accessToken", token,
            "tokenType", "Bearer"
        ));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Clear the SecurityContext
        SecurityContextHolder.clearContext();

        // Remove cookies
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true if using HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expire the cookie immediately
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of(
            "success", "ok",
            "message", "Vous êtes déconnecté"
        ));
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        /**
         * on peut utiliser cela aussi
         * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserPrincipal user = (UserPrincipal) auth.getPrincipal();
         **/
        Map mapRetour;
        if(userPrincipal == null) {
            mapRetour = Map.of(
                "status", "NOT_CONNECTED",
                "message", "Vous nêtes pas connecté"
            ); 
        } else {
            mapRetour = Map.of(
                "email", userPrincipal.getEmail(),
                "roles", userPrincipal.getAuthorities()
            );
        }
        return ResponseEntity.ok(mapRetour);
    }
}
