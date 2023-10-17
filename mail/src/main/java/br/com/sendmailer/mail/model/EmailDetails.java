package br.com.sendmailer.mail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailDetails {
    
    //Destinatario 
    private String recepient;
   
    //Corpo da mensagem
    private String messageBody;
    
    //Assunto
    private String subject;
    
    //Anexo
    private String attachment;
}
