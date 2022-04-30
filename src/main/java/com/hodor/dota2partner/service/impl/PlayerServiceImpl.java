package com.hodor.dota2partner.service.impl;

import com.hodor.dota2partner.model.Player;
import com.hodor.dota2partner.repository.PlayerRepository;
import com.hodor.dota2partner.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.*;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createPlayer(Player player) {

        // id32 = id64 - 76561197960265728
        player.setSteamId32(player.getSteamId64()-76561197960265728);
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        player.setRole("ROLE_USER");
        playerRepository.save(player);
    }
}
