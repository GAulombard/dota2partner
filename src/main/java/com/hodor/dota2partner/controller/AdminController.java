package com.hodor.dota2partner.controller;

import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.service.HeroService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private final HeroService heroService;

    @RolesAllowed({"ADMIN"})
    @PostMapping("/constants/heroes")
    public String getConstantsHeroes(HttpServletRequest servletRequest) throws OpenDotaApiException {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        heroService.saveHeroData();

        return "index";
    }
}
