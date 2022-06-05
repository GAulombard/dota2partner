package com.hodor.dota2partner.service;

import com.hodor.dota2partner.exception.OpenDotaApiException;

public interface HeroService {

    void saveHeroData() throws OpenDotaApiException;
}
