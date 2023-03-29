package com.example.demo.Service;

import com.example.demo.DTO.userDTO;

public class testServiceImpl implements testService {

    @Override
    public String login(userDTO testDTO){

        return "OK";
    }
}
