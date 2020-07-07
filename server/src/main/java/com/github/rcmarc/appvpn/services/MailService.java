// package com.github.rcmarc.appvpn.services;

// import com.github.rcmarc.appvpn.data.NotificationEmail;
// import com.github.rcmarc.appvpn.error.VpnException;
// import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.mail.MailException;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.MimeMessageHelper;
// import org.springframework.mail.javamail.MimeMessagePreparator;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.stereotype.Service;

// @Service
// @AllArgsConstructor
// @Slf4j
// public class MailService {

//     private final JavaMailSender mailSender;

    // @Async
    // public void sendMail(NotificationEmail email) {
    //     MimeMessagePreparator messagePreparator = mimeMessage -> {
    //         MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
    //         messageHelper.setFrom("springreddit@email.com");
    //         messageHelper.setTo(email.getRecipient());
    //         messageHelper.setSubject(email.getSubject());
    //         messageHelper.setText(email.getBody());
    //     };
    //     try {
    //         mailSender.send(messagePreparator);
    //         log.info("Activation email sent!!");
    //     }catch (MailException ex){
    //         throw new VpnException("An error occurred when sending email");
    //     }
    // }

// }
