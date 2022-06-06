package com.hodor.dota2partner.service.impl;

import com.hodor.dota2partner.service.MailContentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
@Slf4j
public class MailContentServiceImpl implements MailContentService {

    private final TemplateEngine templateEngine;

    @Override
    public String build(String message) {
        log.debug("Building mail process");

        Context context = new Context();
        context.setVariable("message", message);

        return templateEngine.process("/mail/mailTemplate", context);
    }
}
