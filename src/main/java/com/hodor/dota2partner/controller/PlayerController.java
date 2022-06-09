package com.hodor.dota2partner.controller;

import com.hodor.dota2partner.dto.AsideHeroRequestDTO;
import com.hodor.dota2partner.dto.PartnerRequestDTO;
import com.hodor.dota2partner.entity.Player;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.exception.PlayerNotFoundException;
import com.hodor.dota2partner.service.PartnerService;
import com.hodor.dota2partner.service.PlayerService;
import com.hodor.dota2partner.util.MedalUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/player")
public class PlayerController {

    private final PlayerService playerService;
    private final PartnerService partnerService;

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping(value = "/home")
    public String getHome(@AuthenticationPrincipal Player principal, Model model, HttpServletRequest servletRequest) throws OpenDotaApiException, PlayerNotFoundException {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");


        playerService.refreshPlayerData(principal.getSteamId32());
        //todo:make a PlayerDTO instead of sending entity information
        Player player = playerService.getPlayer(principal.getSteamId32());

        //todo: do this directly inside the DTO converter or service layer
        String rankIcon = MedalUtil.getRankIconFromRankTier(player.getRankTier());
        String rankStar = MedalUtil.getRankStarFromRankTier(player.getRankTier());
        //todo: do this in service layer instead ?
        List<PartnerRequestDTO> partnerRequestDTOListList = partnerService.searchFriend(principal.getSteamId32())
                .stream()
                .limit(5)
                .collect(Collectors.toList());
        //todo: do this in service layer instead ?
        List<AsideHeroRequestDTO> asideHeroList = playerService.getAsideHeroList(principal.getSteamId32())
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("player", player);
        model.addAttribute("rankIcon", rankIcon);
        model.addAttribute("rankStar", rankStar);
        model.addAttribute("partnerList", partnerRequestDTOListList);
        model.addAttribute("asideHeroList", asideHeroList);

        return "/player/home";
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/refresh-data")
    public String refreshData(@AuthenticationPrincipal Player principal, Model model, HttpServletRequest servletRequest) throws OpenDotaApiException, PlayerNotFoundException {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        playerService.refreshPlayerData(principal.getSteamId32());

        return "redirect:/api/player/home";
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal Player principal, Model model, HttpServletRequest servletRequest) throws PlayerNotFoundException, OpenDotaApiException {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        playerService.refreshPlayerData(principal.getSteamId32());
        //todo:make a PlayerDTO instead of sending entity information
        Player player = playerService.getPlayer(principal.getSteamId32());

        //todo: do this directly inside the DTO converter or service layer
        String rankIcon = MedalUtil.getRankIconFromRankTier(player.getRankTier());
        String rankStar = MedalUtil.getRankStarFromRankTier(player.getRankTier());

        //todo: do this in service layer instead ?
        List<AsideHeroRequestDTO> asideHeroList = playerService.getAsideHeroList(principal.getSteamId32())
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("player", player);
        model.addAttribute("rankIcon", rankIcon);
        model.addAttribute("rankStar", rankStar);
        model.addAttribute("asideHeroList", asideHeroList);

        return "/player/profile";
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/profile/{id}")
    public String getPlayerProfile(@AuthenticationPrincipal Player principal, Model model, HttpServletRequest servletRequest, @PathVariable("id") Long id) throws PlayerNotFoundException, OpenDotaApiException {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        playerService.refreshPlayerData(id);
        //todo:make a PlayerDTO instead of sending entity information
        Player player = playerService.getPlayer(id);

        //todo: do this directly inside the DTO converter or service layer
        String rankIcon = MedalUtil.getRankIconFromRankTier(player.getRankTier());
        String rankStar = MedalUtil.getRankStarFromRankTier(player.getRankTier());

        //todo: do this in service layer instead ?
        List<AsideHeroRequestDTO> asideHeroList = playerService.getAsideHeroList(id)
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("player", player);
        model.addAttribute("rankIcon", rankIcon);
        model.addAttribute("rankStar", rankStar);
        model.addAttribute("asideHeroList", asideHeroList);

        return "/player/playerProfile";
    }

}
