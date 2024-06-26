package com.example.messenger.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailStructure {
    private String from;
    private String to;
    private String subject;
    private String body;
    private String text;
    private String receiverName;
}
