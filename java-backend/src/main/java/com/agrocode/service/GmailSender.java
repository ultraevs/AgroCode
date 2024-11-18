package com.agrocode.service;

import java.util.List;

public class GmailSender {
    private String name;
    private String emailAddress;
    private String emailPassword;

    public GmailSender(String name, String emailAddress, String emailPassword) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.emailPassword = emailPassword;
    }

    public void sendEmail(String subject, String content, List<String> to, List<String> cc, List<String> bcc) throws Exception {
        
    }
}
