package com.subredditstats.subredditstats.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping(value = "/")
    public String getHome() {
        logger.info("/ homepage was accessed");

        return "Hello build week team. This is just the landing page. The interesting stuff is in the API documentation. Check our team's Slack channel for the PDF. Thanks! :)";
    }
}
