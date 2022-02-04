package ru.shemich.auth.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shemich.auth.model.UserApp;
import ru.shemich.auth.repository.UserAppRepository;
import ru.shemich.auth.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private UserAppRepository userAppRepository;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserAppRepository userAppRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userAppRepository = userAppRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        try {
            //при передачи данных мы аутентифицируем пользователя(приложение) на основании логина и пароля
            //токен провайдер создает токен
          String login = request.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(),request.getPassword()));
            UserApp userApp = userAppRepository.findByLogin(request.getLogin()).orElseThrow(() -> new UsernameNotFoundException("Application doesn`t exists"));
            String token = jwtTokenProvider.createToken(request.getLogin(),userApp.getRole().name());
            Map<Object,Object> response = new HashMap<>();
            response.put("login",request.getLogin());
            response.put("token",token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid login/password combination", HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request,response,null);
    }
}
