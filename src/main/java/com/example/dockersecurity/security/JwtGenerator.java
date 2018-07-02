package com.example.dockersecurity.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
@Log4j2
public class JwtGenerator {

    public static final String SECRET = UUID.randomUUID().toString();

    public static final String TOKEN_TYPE_BEARER = "Bearer ";

    public static final String DATA_CLAIM_NAME = "data";

    public static final String SERVICE_NAME = "docker-security";


    @PostConstruct
    public void createIdentity() throws UnsupportedEncodingException {
     log.info("***********");
     log.info(generate());
    }


    public String generate() throws UnsupportedEncodingException {



     return TOKEN_TYPE_BEARER+  JWT.create()
                  .withClaim(DATA_CLAIM_NAME, SERVICE_NAME)
                    .withExpiresAt(Date.from(Instant.now().plus(Duration.ofHours(1))))
                    .withIssuedAt(Date.from(Instant.now()))
                    .sign(Algorithm.HMAC256(SECRET));

    }
}
