package com.example.demo.Controller;

import com.example.demo.Service.SendEmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SendEmailRestcontroller {

    private final SendEmailService sendEmailService;

    @Autowired
    public SendEmailRestcontroller(SendEmailService sendEmailService) {
        this.sendEmailService = sendEmailService;
    }

    @PostMapping("sendemail/contact")
    public void sendEmail(@RequestParam MultiValueMap<String, Object> requestMap,
                          @RequestParam("files") MultipartFile[] multipartFiles) throws MessagingException {
        sendEmailService.sendMail(requestMap, multipartFiles);
    }
}
