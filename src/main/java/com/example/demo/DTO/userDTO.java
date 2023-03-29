package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
/*
    -------------------------
    class 설명
    -------------------------
    user DTO
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
public class userDTO {
    String id;
    String pw;
}
