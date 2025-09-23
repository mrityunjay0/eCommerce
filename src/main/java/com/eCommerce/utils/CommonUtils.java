package com.eCommerce.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class CommonUtils {

    private JavaMailSender javaMailSender;

    public CommonUtils(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean sendMail(String url, String email) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("yaduvanshimrityunjay461@gmail.com", "Shopping Cart");
        helper.setTo(email);

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + url + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject("Reset Your Password");
        helper.setText(content, true);

        javaMailSender.send(message);
        return true;
    }

    public static String generateUrl(HttpServletRequest request) {

        //8080/forgotPassword
        String siteUrl =  request.getRequestURL().toString();

        return siteUrl.replace(request.getServletPath(),"");

    }

}
