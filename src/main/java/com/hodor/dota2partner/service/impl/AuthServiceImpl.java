package com.hodor.dota2partner.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hodor.dota2partner.dto.AuthenticationResponseDTO;
import com.hodor.dota2partner.dto.CreatePlayerDTO;
import com.hodor.dota2partner.dto.LoginRequestDTO;
import com.hodor.dota2partner.entity.NotificationEmail;
import com.hodor.dota2partner.entity.Player;
import com.hodor.dota2partner.entity.Role;
import com.hodor.dota2partner.entity.VerificationToken;
import com.hodor.dota2partner.exception.*;
import com.hodor.dota2partner.repository.PlayerRepository;
import com.hodor.dota2partner.repository.RoleRepository;
import com.hodor.dota2partner.repository.VerificationTokenRepository;
import com.hodor.dota2partner.security.JwtProvider;
import com.hodor.dota2partner.service.AuthService;
import com.hodor.dota2partner.service.MailService;
import com.hodor.dota2partner.serviceopendotaapi.ODPlayersService;
import com.hodor.dota2partner.util.Calculator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PlayerRepository playerRepository;
    private final ODPlayersService oDPlayersService;
    private final DtoConverterServiceImpl dtoConverterService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void signup(CreatePlayerDTO dto) throws EMailAlreadyExistsException, SteamIdNotFoundException, OpenDotaApiException, PlayerNotFoundException {

        Player player = dtoConverterService.CreatePlayerDTOToPlayer(dto);

        if (playerRepository.existsByEmail(player.getEmail()))
            throw new EMailAlreadyExistsException("E-Mail " + player.getEmail() + " already exists");

        long steamId32 = Calculator.steamId64toSteamId32(dto.getSteamId64());
        ObjectNode dataPlayer = oDPlayersService.getPlayerData(steamId32);

        if (dataPlayer.path("profile").path("steamid").asText().isEmpty()) {

            log.error("Steam ID: " + steamId32 + " does not exist");
            throw new SteamIdNotFoundException("Steam ID: " + steamId32 + " does not exist, or not public account");

        } else {

            log.info("Service - Creating new player - steamId32: " + steamId32);
            player.setSteamId32(steamId32);
            player.setCreationDate(Instant.now());
            player.setContributor(false);
            player.setEnabled(false);
            Role role = roleRepository.findByName("ROLE_USER");
            player.setRoles(Arrays.asList(role));
            playerRepository.save(player);
            log.info("Service - Player created");

            String token = generateVerificationToken(player);
            //playerService.refreshPlayerData(steamId32);
            mailService.sendMail(new NotificationEmail("Please activate your account",
                    player.getEmail(),
                    "Thank you !" + "click here" + "http://localhost:8080/api/auth/account-verification/" + token));

        }
    }

    @Override
    @Transactional
    public boolean verifyAccount(String token) throws TokenVerificationException {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);

        verificationToken.orElseThrow(() -> new TokenVerificationException("Invalid Token"));
        //todo: check expiry date
        fetchPlayerAndActivate(verificationToken.get());
        //todo: delete token from DB

        return true;
    }

    @Override
    @Transactional
    public void fetchPlayerAndActivate(VerificationToken verificationToken) {
        Player player = verificationToken.getPlayer();
        player.setEnabled(true);
        playerRepository.save(player);
        log.info("Player account successfully activated");

    }

    @Override
    @Transactional
    public String generateVerificationToken(Player player) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setPlayer(player);
        verificationToken.setExpiryDate(Instant.now().plusSeconds(3600));

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO loginRequestDTO) throws PrivateKeyException {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);

        return new AuthenticationResponseDTO(token, loginRequestDTO.getEmail());
    }
}
