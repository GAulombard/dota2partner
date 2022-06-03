package com.hodor.dota2partner.service.impl;

import com.hodor.dota2partner.service.MailContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentServiceImpl implements MailContentService {

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);

        return templateEngine.process("/mail/mailtoken", context);
    }
}
