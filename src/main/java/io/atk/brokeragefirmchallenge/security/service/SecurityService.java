package io.atk.brokeragefirmchallenge.security.service;

import io.atk.brokeragefirmchallenge.security.config.SecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final SecurityProperties properties;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        String secret = Base64.getEncoder().encodeToString(
                properties.getSecretKey().getBytes(StandardCharsets.UTF_8));
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String accountId, String username, Set<String> roles) {
        Claims claims = Jwts.claims();
        claims.setSubject(accountId);
        claims.setId(UUID.randomUUID().toString());
        claims.put("scope", String.join(" ", roles));
        claims.put("type", properties.getPrefix());
        claims.put("preferred_username", username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + properties.getValidity());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> scope =
                Arrays.stream(claims.get("scope", String.class).split(" "))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());

        User principal = new User(claims.getSubject(), "?", scope);
        return new UsernamePasswordAuthenticationToken(principal, claims, scope);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey).build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ignored) {
        }
        return false;
    }
}
