package br.com.sendmailer.mail.service;

import java.io.File;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.sendmailer.mail.model.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
 
    @Autowired
    private JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String sender;
 

    public String sendMail(EmailDetails details) {
        LOGGER.info("enviando e-mail simples");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(details.getRecepient());
        mailMessage.setText(details.getMessageBody());
        mailMessage.setSubject(details.getSubject());

        javaMailSender.send(mailMessage);

        LOGGER.info("E-Mail enviado com sucesso!");
        return "E-Mail enviado com sucesso!";
    }
    
    public String sendMailWithAttachment(EmailDetails details) {
        LOGGER.info("enviando e-mail com anexo");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true); 
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecepient());
            mimeMessageHelper.setText(details.getMessageBody());
            mimeMessageHelper.setSubject(details.getSubject());
             
            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

            javaMailSender.send(mimeMessage);
            LOGGER.info("E-Mail enviado com sucesso!");
            return "E-Mail enviado com sucesso!";


        } catch (MessagingException e) {
            LOGGER.error("Erro ao enviar o e-mail:", e);
                throw new RuntimeException(e); 
        } 
    }
}
