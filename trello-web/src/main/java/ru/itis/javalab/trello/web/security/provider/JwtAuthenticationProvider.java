package ru.itis.javalab.trello.web.security.provider;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.itis.javalab.trello.api.exception.NotFoundException;
import ru.itis.javalab.trello.impl.models.User;
import ru.itis.javalab.trello.web.security.details.UserDetailsImpl;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtAuthenticationProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    public Optional<Authentication> authenticate(String token) throws NotFoundException {
        if (StringUtils.hasLength(token)){
            try {
                Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
                User user = new User();
                user.setEmail(claims.getBody().getSubject());
                return Optional.of(new UsernamePasswordAuthenticationToken(new UserDetailsImpl(user), "",  Collections.singleton(new SimpleGrantedAuthority("Test"))));
            } catch (JwtException | IllegalArgumentException e) {
                throw new NotFoundException("Unauthorized");
            }

        }
        return Optional.empty();
    }

    public String createToken(String login) {
        Claims claims = Jwts.claims().setSubject(login);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }
}
