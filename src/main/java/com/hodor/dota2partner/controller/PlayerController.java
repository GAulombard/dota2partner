package com.hodor.dota2partner.controller;

import com.hodor.dota2partner.exception.EMailAlreadyExistsException;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.exception.PlayerNotFoundException;
import com.hodor.dota2partner.exception.SteamIdNotFoundException;
import com.hodor.dota2partner.entity.Player;
import com.hodor.dota2partner.dto.CreatePlayerDTO;
import com.hodor.dota2partner.service.FriendService;
import com.hodor.dota2partner.service.PlayerService;
import com.hodor.dota2partner.util.MedalUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@AllArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
    private final FriendService friendService;

    //, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping(value = "/home")
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
