package com.hodor.dota2partner.service;

import com.hodor.dota2partner.dto.CreatePlayerDTO;
import com.hodor.dota2partner.dto.PlayerDTO;
import com.hodor.dota2partner.entity.Player;

public interface DtoConverterService {

    Player CreatePlayerDTOToPlayer(CreatePlayerDTO dto);

    PlayerDTO PlayerToPlayerDTO(Player player);
}
