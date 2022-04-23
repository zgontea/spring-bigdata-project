package com.shop.demo.api;

import java.util.Date;
import java.util.Optional;

import com.shop.demo.config.UserCredencials;
import com.shop.demo.filter.SecretHolder;
import com.shop.demo.model.User;
import com.shop.demo.service.UserManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@RestController
@CrossOrigin
@RequestMapping("auth")
public class AuthApi implements SecretHolder {

    private UserManager userManager;

    @Autowired
    public AuthApi(UserManager userManager) {
        super();
        this.userManager = userManager;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserCredencials user) {
        long currentTimeMiliis = System.currentTimeMillis();
        long expirationTime = 1000 * 60 * 30;

        Optional<User> userFromDatabase = userManager.findByEmail(user.getLogin());

        if (userFromDatabase.isEmpty()) {
            return "Invalid Email";
        }

        if (!BCrypt.checkpw(user.getPassword(), userFromDatabase.get().getPassword())) {
            return "Invalid Password";
        }

        String token = Jwts.builder()
                .setSubject(user.getLogin())
                .claim("roles", "user")
                .setIssuedAt(new Date(currentTimeMiliis))
                .setExpiration(new Date(currentTimeMiliis + expirationTime))
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.encode(jwtSecret))
                .compact();

        return token;
    }
}
