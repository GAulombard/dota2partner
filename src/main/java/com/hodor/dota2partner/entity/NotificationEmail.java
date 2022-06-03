package com.hodor.dota2partner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class NotificationEmail {

    private String subject;
    private String recipient;
    private String body;

}
