package com.hodor.dota2partner.service;

import com.hodor.dota2partner.dto.PartnerRequestDTO;
import com.hodor.dota2partner.exception.OpenDotaApiException;
import com.hodor.dota2partner.entity.Player;

import java.util.List;

public interface PartnerService {

    List<PartnerRequestDTO> searchFriend(long steamId32) throws OpenDotaApiException;

}
