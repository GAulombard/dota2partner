package com.hodor.dota2partner.service.impl;

import com.hodor.dota2partner.exception.PlayerEmailNotFoundException;
import com.hodor.dota2partner.entity.Player;
import com.hodor.dota2partner.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@Slf4j
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PlayerRepository playerRepository;

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        log.info("Process to authentication - email: " + email);

        return playerRepository.findPlayerByEmail(email).orElseThrow(
                () -> new PlayerEmailNotFoundException("Player email: " + email + " not found - authentication failed"));
    }

}
