package com.example.messenger.mail;

public interface MailService {
    void sendMail(String mail, MailStructure mailStructure);

    Boolean sendMailHtml(String mail, MailStructure mailStructure);
}
