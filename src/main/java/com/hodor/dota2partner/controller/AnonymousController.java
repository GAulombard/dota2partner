package com.hodor.dota2partner.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Controller
@Slf4j
public class AnonymousController {


    @GetMapping({"/index", "/"})
    public String getIndex(HttpServletRequest servletRequest) {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - by " + servletRequest.getRemoteUser());


        return "index";
    }

    @GetMapping({"/login"})
    public String getLogin(HttpServletRequest servletRequest) {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - by " + servletRequest.getRemoteUser());

        return "login";
    }
}
