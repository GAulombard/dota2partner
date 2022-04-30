package com.hodor.dota2partner.repository;

import com.hodor.dota2partner.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {

    boolean existsByEmail(@NotEmpty(message = "Email is mandatory") @Email(message = "Email is not valid") String email);

}
