package com.hodor.dota2partner.service.impl;

import com.hodor.dota2partner.exception.PlayerEmailNotFoundException;
import com.hodor.dota2partner.model.Player;
import com.hodor.dota2partner.repository.PlayerRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private final PlayerRepository playerRepository;

    @Autowired
    public MyUserDetailsServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        log.info("Process to authentication - email: "+email);
        Optional<Player> user = playerRepository.findPlayerByEmail(email);

        return user.orElseThrow(() -> new PlayerEmailNotFoundException("Player: "+email+" not found"));
    }
}
