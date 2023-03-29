package com.example.demo.Service;


import com.example.demo.DTO.userDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/*
@Service
@RequiredArgsConstructor

public class CustomUserDetatilService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userDTO testDTO = new userDTO();
        if(testDTO.getId() == null){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        System.out.println("**************Found user***************");
        System.out.println("id : " + testDTO.getId());
        return new UserDetails() {
        };
    	
    }
}*/
