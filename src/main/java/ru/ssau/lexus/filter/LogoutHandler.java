package ru.ssau.lexus.filter;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import ru.ssau.lexus.domain.entity.Token;
import ru.ssau.lexus.repository.crud.TokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
public class LogoutHandler implements LogoutSuccessHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        var token = request.getHeader("Authorization");
        if (token != null) {
            token = token.substring(7);
            var tokenEntity = new Token();
            tokenEntity.setId(token);
            tokenRepository.save(tokenEntity);
        }
    }
}
