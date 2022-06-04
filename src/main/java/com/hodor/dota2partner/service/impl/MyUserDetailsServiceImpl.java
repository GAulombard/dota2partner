package com.hodor.dota2partner.service.impl;

import com.hodor.dota2partner.exception.PlayerEmailNotFoundException;
import com.hodor.dota2partner.entity.Player;
import com.hodor.dota2partner.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private final PlayerRepository playerRepository;

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        log.info("Process to authentication - email: "+email);
        Optional<Player> user = playerRepository.findPlayerByEmail(email);

        return user.orElseThrow(() -> new PlayerEmailNotFoundException("Player email: "+email+" not found - authentication failed"));
    }
}
