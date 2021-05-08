package ru.itis.javalab.trello.web.security.jwt.provider;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.itis.javalab.trello.api.exception.NotFoundException;
import ru.itis.javalab.trello.impl.models.User;
import ru.itis.javalab.trello.web.security.jwt.details.UserDetailsImpl;

import java.util.*;

@Component
public class JwtAuthenticationProvider {

    @Value("${jwt.token.secret}")
    private String SECRET_KEY;

    @Value("${jwt.token.expired}")
    private long VALIDITY;


    public Optional<Authentication> authenticate(String token) throws NotFoundException {
        if (StringUtils.hasLength(token)){
            try {
                Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
                User user = new User();
                user.setEmail(claims.getBody().getSubject());
                return Optional.of(new UsernamePasswordAuthenticationToken(new UserDetailsImpl(user), "",  Collections.singleton(new SimpleGrantedAuthority("USER"))));
            } catch (JwtException | IllegalArgumentException e) {
                throw new NotFoundException("Unauthorized");
            }

        }
        return Optional.empty();
    }

    public String createToken(String login) {
        Claims claims = Jwts.claims().setSubject(login);
        Date now = new Date();
        Date validity = new Date(now.getTime() + VALIDITY);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

    }
}
