package com.demo1.applesson1.services;

public interface MailSenderService {

    void send(String emailTo, String subject, String message);
}
