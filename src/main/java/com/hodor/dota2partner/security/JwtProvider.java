package com.hodor.dota2partner.security;

import com.hodor.dota2partner.entity.Player;
import com.hodor.dota2partner.exception.PrivateKeyException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() throws PrivateKeyException {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/dota2partner.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new PrivateKeyException("Exception occurred while loading keystore");
        }
    }

    public String generateToken(Authentication authentication) throws PrivateKeyException {
        Player principal = (Player) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getEmail())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() throws PrivateKeyException {
        try {
            return (PrivateKey) keyStore.getKey("dota2partner","secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new PrivateKeyException("Exception occurred while retrieving public key from keystore");
        }
    }

}
