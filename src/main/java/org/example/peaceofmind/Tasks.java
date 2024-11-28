package org.example.peaceofmind;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

public class Tasks {
    private RestTemplate restTemplate = new RestTemplate();

//    @Scheduled(cron = "* * * * 1 Mon")
    public void task1() {
//    restTemplate.postForObject("http://localhost:8080/createUser",, String.class);
    }
}