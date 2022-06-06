package com.hodor.dota2partner.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hodor.dota2partner.dto.PartnerRequestDTO;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.repository.PlayerRepository;
import com.hodor.dota2partner.service.PartnerService;
import com.hodor.dota2partner.serviceopendotaapi.impl.ODPlayerServiceImpl;
import com.hodor.dota2partner.util.Calculator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PartnerServiceImpl implements PartnerService {


    private final ODPlayerServiceImpl odPlayersService;
    private final PlayerRepository playerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PartnerRequestDTO> searchFriend(long steamId32) throws OpenDotaApiException {
        log.debug("Fetching partners process");

        List<PartnerRequestDTO> partnerRequestDTOList = new ArrayList<>();

        ArrayNode peers = odPlayersService.getPeers(steamId32);

        peers.iterator().forEachRemaining(peer -> {
            long id = peer.path("account_id").asLong();
            if (playerRepository.existsBySteamId32(id)) {
                //todo: make a builder/mapper
                PartnerRequestDTO partnerRequestDTO = new PartnerRequestDTO();
                partnerRequestDTO.setSteamId32(id);
                partnerRequestDTO.setPersonaName(peer.path("personaname").asText());
                partnerRequestDTO.setRankTier(playerRepository.findPlayerBySteamId32(id).getRankTier());
                partnerRequestDTO.setAvatarFull(peer.path("avatarfull").asText());
                partnerRequestDTO.setEnabled(playerRepository.findPlayerBySteamId32(id).isEnabled());
                partnerRequestDTO.setMatchPlayedWith(peer.path("with_games").asInt());
                partnerRequestDTO.setWinWith(peer.path("with_win").asInt());
                partnerRequestDTO.setWinRateWith(Calculator.winRateCalculator(
                        peer.path("with_win").asInt(),
                        peer.path("with_games").asInt() - peer.path("with_win").asInt()
                ));
                partnerRequestDTOList.add(partnerRequestDTO);
            }
        });

        if (partnerRequestDTOList == null) log.debug("Service - no friends found");

        log.debug("Service - " + partnerRequestDTOList.size() + " friend(s) found");
        log.debug("Fetching partners process successful");
        return partnerRequestDTOList;
    }

}
