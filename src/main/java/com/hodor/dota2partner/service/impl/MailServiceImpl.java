package com.hodor.dota2partner.service.impl;

import com.hodor.dota2partner.entity.NotificationEmail;
import com.hodor.dota2partner.exception.ActivationMailException;
import com.hodor.dota2partner.service.MailContentService;
import com.hodor.dota2partner.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailContentService mailContentService;

    @Override
    @Async
    public void sendMail(NotificationEmail notificationEmail) throws ActivationMailException {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("no-reply@dota2partner.com");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(mailContentService.build(notificationEmail.getBody()));
        };
        try {
            javaMailSender.send(mimeMessagePreparator);
            log.info("Activation email sent !");
        } catch (MailException e) {
            throw new ActivationMailException("For some reasons, we couldn't sent the activation email");
        }
    }
}
