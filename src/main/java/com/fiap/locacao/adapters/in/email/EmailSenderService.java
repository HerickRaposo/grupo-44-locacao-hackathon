package com.fiap.locacao.adapters.in.email;
import com.fiap.locacao.adapters.dto.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private final JavaMailSender javaMailSender;

    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public ResponseEntity<String> enviarEmail(EmailDTO email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.getDestinatario());
            message.setSubject(email.getAssunto());
            message.setText(email.getMensagem());

            javaMailSender.send(message);

            System.out.println("Email enviado com sucesso.");
            return new ResponseEntity<>("Email enviado com sucesso.", HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>("Erro ao envio de email: " + ex.getMessage(), HttpStatus.OK);
        }
    }
}
