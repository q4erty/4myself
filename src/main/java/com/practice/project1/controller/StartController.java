package com.practice.project1.controller;

import com.practice.project1.service.UserGmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/start", "/auth/start"})
public class StartController {

    private final UserGmailService userGmailService;

    @GetMapping("/")
    public String mina(){
        return "Hello World";
    }

    @GetMapping("/secured")
    public String secured(){
        return userGmailService.addUser();
    }

}
