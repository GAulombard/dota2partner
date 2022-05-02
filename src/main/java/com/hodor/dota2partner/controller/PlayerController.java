package com.hodor.dota2partner.controller;

import com.hodor.dota2partner.exception.EMailAlreadyExistsException;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.exception.SteamIdNotFoundException;
import com.hodor.dota2partner.model.Player;
import com.hodor.dota2partner.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/player")
public class PlayerController {

    //TODO: swagger doc
    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/validate")
    //@ApiOperation(value = "This URI allows to save a new user in the database")
    public String createNewPlayer(@AuthenticationPrincipal Player principal, @Valid @RequestBody Player player, BindingResult bindingResult) throws SteamIdNotFoundException, OpenDotaApiException, EMailAlreadyExistsException {
        log.info("HTTP POST Request received at /player/validate");

        if(bindingResult.hasErrors()) {
            log.error("binding result error"+bindingResult.getFieldError());
            return "redirect:/player/validate";
        }

        playerService.createPlayer(player);

        return "redirect:/login";
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/home")
    public String getHome(@AuthenticationPrincipal Player principal, Model model) {
        log.info("HTTP GET Request received at /player/home - " + principal.getPersonaName());

        model.addAttribute("player", principal);

        return "/player/home";
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/refresh-data")
    public String refreshData(@AuthenticationPrincipal Player principal, Model model) throws OpenDotaApiException {
        log.info("HTTP GET Request received at /player/refresh-data - " + principal.getPersonaName());

        playerService.refreshPlayerData(principal.getSteamId32());

        return "redirect:/player/home";
    }

}
