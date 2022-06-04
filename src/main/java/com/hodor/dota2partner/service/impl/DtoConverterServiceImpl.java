package com.hodor.dota2partner.service.impl;

import com.hodor.dota2partner.dto.PlayerDTO;
import com.hodor.dota2partner.entity.Player;
import com.hodor.dota2partner.dto.CreatePlayerDTO;
import com.hodor.dota2partner.service.DtoConverterService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DtoConverterServiceImpl implements DtoConverterService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Player CreatePlayerDTOToPlayer(CreatePlayerDTO dto) {

        Player player = new Player();

        player.setSteamId64(dto.getSteamId64());
        player.setEmail(dto.getEmail());
        player.setPassword(passwordEncoder.encode(dto.getPassword()));

        return player;
    }

    @Override
    public PlayerDTO PlayerToPlayerDTO(Player player) {

        PlayerDTO dto = new PlayerDTO();

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
