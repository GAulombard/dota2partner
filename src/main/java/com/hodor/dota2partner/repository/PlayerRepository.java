package com.hodor.dota2partner.repository;

import com.hodor.dota2partner.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {

    boolean existsByEmail(@NotEmpty(message = "Email is mandatory") @Email(message = "Email is not valid") String email);

    Optional<Player> findPlayerByEmail(String eMail);

    Player findPlayerBySteamId32(Long steamId32);

    boolean existsBySteamId32(Long steamId32);
}
