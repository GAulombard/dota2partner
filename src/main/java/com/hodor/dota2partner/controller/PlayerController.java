package com.hodor.dota2partner.controller;

import com.hodor.dota2partner.exception.EMailAlreadyExistsException;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.exception.PlayerNotFoundException;
import com.hodor.dota2partner.exception.SteamIdNotFoundException;
import com.hodor.dota2partner.model.Player;
import com.hodor.dota2partner.dto.CreatePlayerDto;
import com.hodor.dota2partner.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/player")
public class PlayerController {

    //TODO: swagger doc
    @Autowired
    private PlayerService playerService;

    @PostMapping("/validate")
    //@ApiOperation(value = "This URI allows to save a new user in the database")
    public String createNewPlayer(@AuthenticationPrincipal Player principal,
                                  @Valid @RequestBody CreatePlayerDto playerDto,
                                  BindingResult bindingResult,
                                  HttpServletRequest servletRequest) throws SteamIdNotFoundException, OpenDotaApiException, EMailAlreadyExistsException, PlayerNotFoundException {

        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - by " + servletRequest.getRemoteUser());

        if (bindingResult.hasErrors()) {
            log.error("binding result error" + bindingResult.getFieldError());
            return "redirect:/player/validate";
        }

        playerService.createPlayer(playerDto);

        return "redirect:/login";
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/home")
    public String getHome(@AuthenticationPrincipal Player principal, Model model, HttpServletRequest servletRequest) {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - by " + servletRequest.getRemoteUser());

        Player player = playerService.getPlayer(principal.getSteamId32());

        model.addAttribute("player", player);

        return "/player/home";
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/refresh-data")
    public String refreshData(@AuthenticationPrincipal Player principal, Model model, HttpServletRequest servletRequest) throws OpenDotaApiException, PlayerNotFoundException {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - by " + servletRequest.getRemoteUser());

        playerService.refreshPlayerData(principal.getSteamId32());

        return "redirect:/player/home";
    }

}
