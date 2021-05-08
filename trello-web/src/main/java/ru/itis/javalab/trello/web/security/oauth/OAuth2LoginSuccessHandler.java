package ru.itis.javalab.trello.web.security.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.itis.javalab.trello.api.dto.UserDto;
import ru.itis.javalab.trello.api.services.UserService;
import ru.itis.javalab.trello.impl.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;

    @Autowired
    public OAuth2LoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {
        System.out.println(authentication.getPrincipal());
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        String name = oAuth2User.getName();
        String surname = oAuth2User.getSurname();
        Optional<UserDto> userDto =  userService.getUserByEmail(email);
        if (userDto == null) {
            userService.signUpAfterOAuth(email, name, surname, User.AuthProvider.GOOGLE.toString());
        } else {
            userService.updateUserAfterOAuth(userDto, name, User.AuthProvider.GOOGLE.toString());
        }
        if (oAuth2User.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/home");
        }
    }
}
