package com.example.demo.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class SendEmailService {
    private JavaMailSender mailSender;
    public void sendMail(MultiValueMap<String, Object> requestMap, MultipartFile[] multipartFiles) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String subject = (String) requestMap.getFirst("subject");
        String email = (String) requestMap.getFirst("email");
        String content = (String) requestMap.getFirst("content");
        try {
            helper.setSubject(subject);
            helper.setFrom("nghia270902@gmail.com", "Nông Nghĩa");
            helper.setTo(email);
            helper.setText(content, true); // HTML content

        for (MultipartFile file : multipartFiles) {
            if (file != null && !file.isEmpty()) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                InputStreamSource source = new InputStreamSource() {
                    @Override
                    public InputStream getInputStream() throws IOException {
                        return file.getInputStream();
                    }
                };
                helper.addAttachment(fileName, source);
            }
        }

        mailSender.send(message);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
