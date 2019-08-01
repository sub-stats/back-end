package com.subredditstats.subredditstats.controllers;

import com.subredditstats.subredditstats.SubredditStatsApplication;
import com.subredditstats.subredditstats.dtos.LoginStatus;
import com.subredditstats.subredditstats.dtos.RegistrationStatus;
import com.subredditstats.subredditstats.models.LoginRequest;
import com.subredditstats.subredditstats.models.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @CrossOrigin
    @PostMapping(value = "/login", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws NoSuchAlgorithmException {
        logger.info("/login with username: " + loginRequest.getUsername());

        LoginStatus loginStatus = new LoginStatus();

        String hashedPassword = hashPassword(loginRequest.getPassword());
        if (!SubredditStatsApplication.usernameToPassword.containsKey(loginRequest.getUsername()) ||
                !SubredditStatsApplication.usernameToPassword.get(loginRequest.getUsername()).equals(hashedPassword)) {
            loginStatus.setSuccess(false);
            loginStatus.setReason("Username or password invalid");
            return new ResponseEntity<>(loginStatus, HttpStatus.BAD_REQUEST);
        }

        int token =  (loginRequest.getUsername() + loginRequest.getPassword()).hashCode();
        String stringToken = Integer.toString(token);

        loginStatus.setSuccess(true);
        loginStatus.setToken(stringToken);
        SubredditStatsApplication.tokens.add(stringToken);

        return new ResponseEntity<>(loginStatus, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "/register", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) throws NoSuchAlgorithmException {
        logger.info("/register with username: " + registerRequest.getUsername());

        RegistrationStatus registrationStatus = new RegistrationStatus();


        if (SubredditStatsApplication.usernameToPassword.containsKey(registerRequest.getUsername())) {
            registrationStatus.setSuccess(false);
            registrationStatus.setReason("Username taken");
            return new ResponseEntity<>(registrationStatus, HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = hashPassword(registerRequest.getPassword());

        SubredditStatsApplication.usernameToPassword.put(registerRequest.getUsername(), hashedPassword);
        registrationStatus.setSuccess(true);

        return new ResponseEntity<>(registrationStatus, HttpStatus.OK);
    }

    // Implementation inspired by https://www.mkyong.com/java/java-md5-hashing-example/
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
