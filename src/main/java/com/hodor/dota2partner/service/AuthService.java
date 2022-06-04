package com.hodor.dota2partner.service;

import com.hodor.dota2partner.dto.AuthenticationResponseDTO;
import com.hodor.dota2partner.dto.CreatePlayerDTO;
import com.hodor.dota2partner.dto.LoginRequestDTO;
import com.hodor.dota2partner.entity.Player;
import com.hodor.dota2partner.entity.VerificationToken;
import com.hodor.dota2partner.exception.*;

public interface AuthService {

    void signup(CreatePlayerDTO dto) throws EMailAlreadyExistsException, SteamIdNotFoundException, OpenDotaApiException, PlayerNotFoundException;

    boolean verifyAccount(String token) throws TokenVerificationException;

    void fetchPlayerAndActivate(VerificationToken verificationToken);

    String generateVerificationToken(Player player);

    AuthenticationResponseDTO login(LoginRequestDTO loginRequestDTO) throws PrivateKeyException;
}
