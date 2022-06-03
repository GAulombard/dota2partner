package com.hodor.dota2partner.service;

import com.hodor.dota2partner.entity.NotificationEmail;
import com.hodor.dota2partner.exception.ActivationMailException;
import org.springframework.stereotype.Service;

public interface MailService {

    void sendMail(NotificationEmail notificationEmail) throws ActivationMailException;
}
