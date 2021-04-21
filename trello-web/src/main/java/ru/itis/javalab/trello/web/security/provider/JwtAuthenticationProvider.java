package ru.itis.javalab.trello.web.security.provider;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.itis.javalab.trello.api.exception.NotFoundException;
import ru.itis.javalab.trello.impl.models.User;
import ru.itis.javalab.trello.web.security.details.UserDetailsImpl;

import java.util.*;

@Component
public class JwtAuthenticationProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    @Value("${jwt.expiredRefresh}")
    private long refreshExpirationDate;

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

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> li = new ArrayList<>();
        for (GrantedAuthority a: userDetails.getAuthorities()) {
            li.add(a.getAuthority());
        }
        claims.put("role",li);
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDate * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDate * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}
