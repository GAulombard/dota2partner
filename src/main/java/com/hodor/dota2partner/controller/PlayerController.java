package com.hodor.dota2partner.controller;

import com.hodor.dota2partner.exception.EMailAlreadyExistsException;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.exception.PlayerNotFoundException;
import com.hodor.dota2partner.exception.SteamIdNotFoundException;
import com.hodor.dota2partner.model.Player;
import com.hodor.dota2partner.dto.CreatePlayerDTO;
import com.hodor.dota2partner.service.FriendService;
import com.hodor.dota2partner.service.PlayerService;
import com.hodor.dota2partner.util.MedalUtil;
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
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/player")
public class PlayerController {

    //TODO: swagger doc
    @Autowired
    private PlayerService playerService;
    @Autowired
    private FriendService friendService;

    @PostMapping("/validate")
    //@ApiOperation(value = "This URI allows to save a new user in the database")
    public String createNewPlayer(@AuthenticationPrincipal Player principal,
                                  @Valid @ModelAttribute("player") CreatePlayerDTO player,
                                  BindingResult bindingResult,
                                  HttpServletRequest servletRequest) throws SteamIdNotFoundException, OpenDotaApiException, EMailAlreadyExistsException, PlayerNotFoundException {

        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        if (bindingResult.hasErrors()) {
            log.error("binding result error" + bindingResult.getFieldError());
            return "signup";
        }

        playerService.createPlayer(player);

        return "redirect:/player/home";
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/home")
    public String getHome(@AuthenticationPrincipal Player principal, Model model, HttpServletRequest servletRequest) throws OpenDotaApiException, PlayerNotFoundException {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        playerService.refreshPlayerData(principal.getSteamId32());
        Player player = playerService.getPlayer(principal.getSteamId32());
        String rankIcon = MedalUtil.getRankIconFromRankTier(player.getRankTier());
        String rankStar = MedalUtil.getRankStarFromRankTier(player.getRankTier());

        List<Player> friendList = friendService.searchFriend(principal.getSteamId32());

        model.addAttribute("player", player);
        model.addAttribute("rankIcon", rankIcon);
        model.addAttribute("rankStar", rankStar);
        model.addAttribute("friendList", friendList);

        return "/player/home";
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/refresh-data")
    public String refreshData(@AuthenticationPrincipal Player principal, Model model, HttpServletRequest servletRequest) throws OpenDotaApiException, PlayerNotFoundException {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        playerService.refreshPlayerData(principal.getSteamId32());

        return "redirect:/player/home";
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal Player principal, Model model, HttpServletRequest servletRequest) {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        Player player = playerService.getPlayer(principal.getSteamId32());
        String rankIcon = MedalUtil.getRankIconFromRankTier(player.getRankTier());
        String rankStar = MedalUtil.getRankStarFromRankTier(player.getRankTier());

        model.addAttribute("player", player);
        model.addAttribute("rankIcon", rankIcon);
        model.addAttribute("rankStar", rankStar);

        return "/player/profile";
    }

}
