package ru.itis.javalab.trello.impl.utlis;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.javalab.trello.impl.models.User;

import java.util.Calendar;
import java.util.Date;

@Component
public class TokenGenerator {

    private final String SECRET_KEY = "realsanya";

    public Long getCurrentExpiringDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 1);
        return cal.getTimeInMillis();
    }

    public String createAccessToken(User user) {
        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("role", user.getRole().toString())
                .withClaim("state", user.getState().toString())
                .withClaim("email", user.getEmail())
                .withClaim("expiringTime", getCurrentExpiringDate())
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public User verifyToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);

            if (isExpired(new Date(Long.parseLong(String.valueOf(decodedJWT.getClaim("expiringTime")))))) {
                throw new JWTVerificationException("Token is expired");
            }

            return User.builder()
                    .email(decodedJWT.getClaim("email").toString().substring(1, decodedJWT.getClaim("email").toString().length() - 1))
                    .role(roleConverter(decodedJWT.getClaim("role").toString()))
                    .state(stateConverter(decodedJWT.getClaim("state").toString()))
                    .build();

        } catch (JWTVerificationException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public User.Role roleConverter(String role) {
        if ("ADMIN".equals(role)) {
            return User.Role.ADMIN;
        }
        return User.Role.USER;
    }

    public User.State stateConverter(String state) {
        if ("ACTIVE".equals(state)) {
            return User.State.ACTIVE;
        }
        return User.State.BANNED;
    }

    public boolean isExpired(Date tokenDate) {
        return tokenDate.before(new Date());
    }
}
