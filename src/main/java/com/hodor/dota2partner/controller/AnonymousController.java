package com.hodor.dota2partner.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class AnonymousController {

    @GetMapping({"/index","/"})
    public String getIndex() {
        log.info("HTTP GET request received at /index");

        return "index";
    }
}
