package com.hodor.dota2partner.security;

import com.hodor.dota2partner.entity.Player;
import com.hodor.dota2partner.exception.PrivateKeyException;
import com.hodor.dota2partner.exception.PublicKeyException;
import com.hodor.dota2partner.util.EnvironmentProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parser;

@Service
@Slf4j
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() throws PrivateKeyException {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/dota2partner.jks");
            keyStore.load(resourceAsStream, EnvironmentProperties.retrieveEnvironmentProperty("KEY_STORE").toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new PrivateKeyException("Exception occurred while loading keystore");
        }
    }

    public String generateToken(Authentication authentication) throws PrivateKeyException {
        log.info("generating token");

        Player principal = (Player) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getEmail())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() throws PrivateKeyException {
        try {
            return (PrivateKey) keyStore.getKey("dota2partner", EnvironmentProperties.retrieveEnvironmentProperty("KEY_STORE").toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | IOException e) {
            throw new PrivateKeyException("Exception occurred while retrieving private key from keystore");
        }
    }

    public boolean validateToken(String jwt) throws PublicKeyException {
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() throws PublicKeyException {
        try {
            return keyStore.getCertificate("dota2partner").getPublicKey();
        } catch (KeyStoreException e) {
            throw new PublicKeyException("Exception occurred while retrieving public key from keystore");
        }
    }

    public String getUserEmailFromJwt(String token) throws PublicKeyException {
        Claims claims = parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
