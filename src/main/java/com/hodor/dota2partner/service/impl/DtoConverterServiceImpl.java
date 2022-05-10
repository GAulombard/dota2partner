package com.hodor.dota2partner.service.impl;

import com.hodor.dota2partner.model.Player;
import com.hodor.dota2partner.model.dto.CreatePlayerDto;
import com.hodor.dota2partner.service.DtoConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class DtoConverterServiceImpl implements DtoConverterService {

    private final PasswordEncoder passwordEncoder;

    public DtoConverterServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Player CreatePlayerDtoToPlayer(CreatePlayerDto dto) {

        Player player = new Player();

        player.setSteamId64(dto.getSteamId64());
        player.setEmail(dto.getEmail());
        player.setPassword(passwordEncoder.encode(dto.getPassword()));

        return player;
    }
}
