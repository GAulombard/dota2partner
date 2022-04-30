package com.hodor.dota2partner.service.impl;

import com.hodor.dota2partner.model.Player;
import com.hodor.dota2partner.repository.PlayerRepository;
import com.hodor.dota2partner.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void createPlayer(Player player) {
        playerRepository.save(player);
    }
}
