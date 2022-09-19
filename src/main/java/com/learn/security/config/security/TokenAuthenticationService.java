package com.learn.security.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TokenAuthenticationService {

    static final String SECRET = "MySecret";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_NAME = "Authorization";

    public static void addAuthentication(HttpServletResponse httpServletResponse, Authentication authentication) {
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String JWT = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authorities)
                .setExpiration(getExpirationTime())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        httpServletResponse.setHeader(HEADER_NAME, TOKEN_PREFIX + JWT);
    }

    private static Date getExpirationTime() {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.HOUR_OF_DAY, 1);
        return instance.getTime();
    }

    public static Authentication getAuthentication(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HEADER_NAME);

        if (token != null) {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            List<String> authorities = body.get("authorities", List.class);

            List<GrantedAuthority> grantedAuthorities = authorities.stream()
                    .map(authority -> (GrantedAuthority) () -> authority)
                    .collect(Collectors.toList());

            String user = body.getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
            }
        }

        return null;
    }
}
