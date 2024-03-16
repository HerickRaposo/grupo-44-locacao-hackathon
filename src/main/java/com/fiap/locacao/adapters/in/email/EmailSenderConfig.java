package com.fiap.locacao.adapters.in.email;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import java.util.Properties;

@Configuration
public class EmailSenderConfig {

    @Value("${EMAIL_HOST}")
    private String emailHost;

    @Value("${EMAIL_PORT}")
    private int emailPort;

    @Value("${EMAIL_USER}")
    private String emailUser;

    @Value("${EMAIL_PASSWORD}")
    private String emailPassword;

    private Environment env;

    public EmailSenderConfig (Environment env){
        this.env = env;
    }

//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(env.getRequiredProperty("EMAIL_HOST"));
//        mailSender.setPort(Integer.parseInt(env.getRequiredProperty("EMAIL_PORT"))); // Porta padrão para SMTP do Outlook
//        mailSender.setUsername(env.getRequiredProperty("EMAIL_USER"));
//        mailSender.setPassword(env.getRequiredProperty("EMAIL_PASSWORD"));

//        mailSender.setHost();
//        mailSender.setPort(Integer.parseInt(env.getRequiredProperty("EMAIL_PORT"))); // Porta padrão para SMTP do Outlook
//        mailSender.setUsername(env.getRequiredProperty("EMAIL_USER"));
//        mailSender.setPassword(env.getRequiredProperty("EMAIL_PASSWORD"));

        @Bean
        public JavaMailSender javaMailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(emailHost);
            mailSender.setPort(emailPort);
            mailSender.setUsername(emailUser);
            mailSender.setPassword(emailPassword);
            return mailSender;
            // pok
        }

//       Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;

}