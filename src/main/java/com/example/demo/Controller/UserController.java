package com.example.demo.Controller;


import com.example.demo.DTO.userDTO;
import com.example.demo.Util.JwtTokenProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
/*
    -------------------------
    class 설명
    -------------------------
    사용자 Login Controller
 */
@RestController
public class UserController {

    private final Logger Logger = LogManager.getLogger(this.getClass());
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public String login(@RequestBody userDTO userDTO ) {
        String uPK= userDTO.getId();
        String roles = "admin";
        HttpHeaders headers = new HttpHeaders();
        return new JwtTokenProvider().createToken(uPK,roles);
    }
}
