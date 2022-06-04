package com.hodor.dota2partner.service;

import com.hodor.dota2partner.dto.AuthenticationResponse;
import com.hodor.dota2partner.dto.CreatePlayerDTO;
import com.hodor.dota2partner.dto.LoginRequest;
import com.hodor.dota2partner.entity.Player;
import com.hodor.dota2partner.entity.VerificationToken;
import com.hodor.dota2partner.exception.*;

public interface AuthService {

    void signup(CreatePlayerDTO dto) throws EMailAlreadyExistsException, SteamIdNotFoundException, OpenDotaApiException, PlayerNotFoundException;

    boolean verifyAccount(String token) throws TokenVerificationException;

    void fetchPlayerAndActivate(VerificationToken verificationToken);

    String generateVerificationToken(Player player);

    AuthenticationResponse login(LoginRequest loginRequest) throws PrivateKeyException;
}
