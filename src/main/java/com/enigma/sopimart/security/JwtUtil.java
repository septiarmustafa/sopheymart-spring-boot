package com.enigma.sopimart.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.sopimart.entity.AppUser;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    // tempat generate token - header - body - signature
    // get data by username
    // validation

    private final String jwtSecret = "Secret";
    private final String appName = "Sopimart App";

    public String generateToken (AppUser appUser){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            String token = JWT.create()
                    .withIssuer(appName) // info untuk application nama yang kita buat
                    .withSubject(appUser.getId()) // menentukan object yang akan dibuat biasanya dari ID
                    .withExpiresAt(Instant.now().plusSeconds(60)) // menentukan waktu kadaluarsa token nanti, dalam sini kadaluarsanya adalah 60 detik setelah dibuat
                    .withIssuedAt(Instant.now()) // menetapkan waktu token kapan dibuat
                    .withClaim("role", appUser.getRole().name()) // menambahkan claim atau info nama pengguna
                    .sign(algorithm); // ini itu seperti ttd kontrak bahwa algoritma yang kita pakai itu udah pasti HMAC256
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException();
        }
    }

    public boolean verifyJwtToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuer().equals(appName);
        } catch (JWTVerificationException e) {
            throw new RuntimeException();
        }
    }

    public Map<String, String> getUserInfoByToken (String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("userId", decodedJWT.getSubject());
            userInfo.put("role", decodedJWT.getClaim("role").asString());
            return userInfo;
        } catch (JWTVerificationException e) {
            throw new RuntimeException();
        }
    }
}
