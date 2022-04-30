package com.hodor.dota2partner.controller;

import com.hodor.dota2partner.model.Player;
import com.hodor.dota2partner.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/player")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/validate")
    @Transactional
    public String createNewPlayer(@AuthenticationPrincipal Player principal, @Valid @RequestBody Player player, BindingResult bindingResult){
        log.info("HTTP POST Request received at /player/validate");

        log.info(""+player.toString());

        if(bindingResult.hasErrors()) {
            log.error("binding result error"+bindingResult.getFieldError());
            return "binding result error"+bindingResult.getFieldError();
        }

        playerService.createPlayer(player);

        return "new player created";
    }
}
