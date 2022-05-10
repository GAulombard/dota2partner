package com.hodor.dota2partner.service.impl;

import com.hodor.dota2partner.dto.PlayerDto;
import com.hodor.dota2partner.model.Player;
import com.hodor.dota2partner.dto.CreatePlayerDto;
import com.hodor.dota2partner.service.DtoConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class DtoConverterServiceImpl implements DtoConverterService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Player CreatePlayerDtoToPlayer(CreatePlayerDto dto) {

        Player player = new Player();

        player.setSteamId64(dto.getSteamId64());
        player.setEmail(dto.getEmail());
        player.setPassword(passwordEncoder.encode(dto.getPassword()));

        return player;
    }

    public PlayerDto PlayerToPlayerDto(Player player) {

        PlayerDto dto = new PlayerDto();

        dto.setPlayerId(player.getPlayerId());
        dto.setSteamId32(player.getSteamId32());
        dto.setPersonaName(player.getPersonaName());
        dto.setRankTier(player.getRankTier());
        dto.setWin(player.getWin());
        dto.setLoss(player.getLoss());
        dto.setWinRate(player.getWinRate());

        return dto;
    }
}
