package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/port")
    public String getPort() {
        return serverPort;
    }

    @GetMapping("sum-million")
    public Long getSumToMillion() {
        long n = 1_000_000;
        return n * (n + 1) / 2;
    }
}