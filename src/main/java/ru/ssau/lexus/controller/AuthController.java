package ru.ssau.lexus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ssau.lexus.security.JwtTokenUtil;
import ru.ssau.lexus.service.UserService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userServiceExt;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Value("${password.salt:0}")
    public long salt;

    @PostMapping("/signIn")
    public ResponseEntity<String> createAuthenticationToken(String password, String login) throws Exception {
        authenticate(login, password + salt);
        UserDetails userDetails = userServiceExt.loadUserByUsername(login);
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(token);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
