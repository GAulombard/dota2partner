package com.hodor.dota2partner.controller;

import com.hodor.dota2partner.dto.AuthenticationResponseDTO;
import com.hodor.dota2partner.dto.CreatePlayerDTO;
import com.hodor.dota2partner.dto.LoginRequestDTO;
import com.hodor.dota2partner.entity.Player;
import com.hodor.dota2partner.exception.*;
import com.hodor.dota2partner.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signup")
    public String getSignupForm(HttpServletRequest servletRequest, Model model) {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        model.addAttribute("player", new CreatePlayerDTO());

        return "signup";
    }

    @PostMapping("/signup")
    public String createNewPlayer(@AuthenticationPrincipal Player principal,
                                  @Valid @ModelAttribute("player") CreatePlayerDTO player,
                                  BindingResult bindingResult,
                                  HttpServletRequest servletRequest, Model model) throws SteamIdNotFoundException, OpenDotaApiException, EMailAlreadyExistsException, PlayerNotFoundException {

        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        if (bindingResult.hasErrors()) {
            log.error("binding result error" + bindingResult.getFieldError());
            return "signup";
        }

        authService.signup(player);
        String message = "We sent you an email with an activation link. Click the link to activate your account.";
        model.addAttribute("message", message);

        return "/mail/activationmailsent";
    }

    @GetMapping("account-verification/{token}")
    public String verifyAccount(@PathVariable String token, HttpServletRequest servletRequest) throws TokenVerificationException {

        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        if (!authService.verifyAccount(token)) throw new TokenVerificationException("Something went wrong !");

        return "login";
    }

    @GetMapping({"/index", "/"})
    public String getIndex(HttpServletRequest servletRequest) {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        return "index";
    }

    @GetMapping({"/login"})
    public String getLoginPage(HttpServletRequest servletRequest, Model model) {
        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        model.addAttribute("loginRequest", new LoginRequestDTO());


        return "login";
    }

    @PostMapping(value = "/login")
    public String login(@Valid @ModelAttribute("loginRequest") LoginRequestDTO loginRequestDTO, HttpServletRequest servletRequest) throws PrivateKeyException {

        log.info("HTTP " + servletRequest.getMethod() +
                " request received at " + servletRequest.getRequestURI() +
                " - [" + (servletRequest.getRemoteUser() == null ? "anonymous user" : servletRequest.getRemoteUser()) + "]");

        //todo:implement validation form to check if player found or not, etc...

        AuthenticationResponseDTO authenticationResponseDTO = authService.login(loginRequestDTO);
        log.info("Authentication response: " + authenticationResponseDTO.getAuthenticationToken() + " " + authenticationResponseDTO.getEmail());

        return "redirect:/player/home";
    }

}
