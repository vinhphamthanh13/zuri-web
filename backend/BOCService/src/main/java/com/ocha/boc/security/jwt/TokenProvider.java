package com.ocha.boc.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import com.ocha.boc.model.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.ocha.boc.constant.AuthoritiesConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenProvider {

	public static final String ADMIN = "boc@gmail.com";
	
	public String registrationToken = "";
	
    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private final Base64.Encoder encoder = Base64.getEncoder();

    private String secretKey;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

	@Value("${base64.secret}")
	String base64Secret;
	
	@Value("${token.validity.in.seconds}")
	Long tokenValidityInSeconds;
	
	@Value("${token.validity.in.seconds.for.remember.me}")
	Long tokenValidityInSecondsForRememberMe;
	
    @PostConstruct
    public void init() {
        this.secretKey = encoder.encodeToString(base64Secret.getBytes(StandardCharsets.UTF_8));
        this.tokenValidityInMilliseconds =
            1000 * tokenValidityInSeconds;
        this.tokenValidityInMillisecondsForRememberMe =
            1000 * tokenValidityInSecondsForRememberMe;
        registrationToken = createToken("rao@quezone.com.au", AuthoritiesConstants.ADMIN, true, 0);
        log.info("registrationToken {}", registrationToken);
    }
    
    public String getRegistrationToken() {
		return registrationToken;
	}

    public String checkNewToken() {
    	if(!validateToken(registrationToken)) {
    		 registrationToken = createToken(ADMIN, AuthoritiesConstants.ADMIN, true, 0);
    	     log.info("renewToken {}", registrationToken);
    	}
    	return registrationToken;
    }

	public void setRegistrationToken(String registrationToken) {
		this.registrationToken = registrationToken;
	}

	public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }
        log.debug("authentication {}", authentication.getName());

        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setExpiration(validity)
            .compact();
    }

    public String createToken(String email, String authoritiesConstant,  boolean rememberMe, Integer expiresIn) {

        Authority userAuthority = new Authority();
        userAuthority.setName(authoritiesConstant.toString());
        Set<Authority> setAuthorities = new HashSet<>();
        setAuthorities.add(userAuthority);
        
    	 String authorities = setAuthorities.stream()
    	            .map(Authority::getName)
    	            .collect(Collectors.joining(","));
    	 
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + (expiresIn == 0 ? this.tokenValidityInMilliseconds : expiresIn));
        }
        log.debug("authentication {}", email);

        return Jwts.builder()
            .setSubject(email)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setExpiration(validity)
            .compact();
    }
    
    public String createToken(String email, Set<Authority> pAuthorities, boolean rememberMe, Integer expiresIn) {

    	 String authorities = pAuthorities.stream()
    	            .map(Authority::getName)
    	            .collect(Collectors.joining(","));
    	 
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + (expiresIn == 0 ? this.tokenValidityInMilliseconds : expiresIn));
        }
        log.debug("authentication {}", email);

        return Jwts.builder()
            .setSubject(email)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setExpiration(validity)
            .compact();
    }

    
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
}
