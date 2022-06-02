package com.hodor.dota2partner.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @RolesAllowed({"ADMIN"})
    @PostMapping("/constants/heroes")
    public void getConstantsHeroes(HttpServletRequest servletRequest) {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

    }
}
