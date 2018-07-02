package com.example.dockersecurity.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

import static com.example.dockersecurity.security.JwtGenerator.DATA_CLAIM_NAME;
import static com.example.dockersecurity.security.JwtGenerator.SECRET;
import static com.example.dockersecurity.security.JwtGenerator.SERVICE_NAME;


@Component
public class JwtValidator {


    public void validate(String token) throws UnsupportedEncodingException {

        JWT.require(Algorithm.HMAC256(SECRET))
                .withClaim(DATA_CLAIM_NAME, SERVICE_NAME)
                .build()
                .verify(token);

    }
}
