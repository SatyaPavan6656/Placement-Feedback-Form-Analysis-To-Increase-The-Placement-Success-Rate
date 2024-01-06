package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.models.Signup;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    //Send a congratulations mail to User after Signing up
    public void sendCongratulationsEmail(@ModelAttribute Signup s,String toAddress) throws MessagingException {
    	MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("miniprojectcseb2023@gmail.com");
        helper.setTo(toAddress);
        helper.setSubject("Congratulations on Signing Up with us!");

        // Construct the email body with HTML
        String htmlContent = "<h1>Registration Successful!</h1>"
                + "<p style=\"font-size:20px;color:green;\">"
                + "Welcome Mr/Ms. <span style=\"font-weight: bold;\">" + s.getUsername() + "</span>"
                + "</p>"
                + "<div style=\"display: flex; justify-content: center; align-items: center; margin-top: 50px;\">"
                + "<div style=\"width: 80px; height: 80px; background-color: #4caf50; border-radius: 50%; "
                + "display: inline-flex; justify-content: center; align-items: center; margin-right: 20px;\"></div>"
                + "<p>Thank you for registering.</p>"+"<br><br>"
                + "</div>"
                + "<a href=\"/signup\" style=\"padding: 10px 20px; font-size: 18px; background-color: #337ab7; color: white; "
                + "border: none; border-radius: 5px; text-decoration: none; cursor: pointer; "
                + "transition: background-color 0.3s ease;\">Go to Login</a>";

        // Set the HTML content as the message text
        helper.setText(htmlContent,true);

        // Send the email
        javaMailSender.send(message);
    }

    //Send a email to admin as notification when a user signs up
	public void sendAdminNotificationEmail(@ModelAttribute Signup S) throws MessagingException {
		// TODO Auto-generated method stub
		MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("miniprojectcseb2023@gmail.com");
        helper.setTo("21a81a0590@sves.org.in");
        helper.setSubject("A New User Sign Up to the Page");

        // Construct the email body with HTML
        String htmlContent = "<h1>New User Signed Up!!!!</h1>"
                + "<p style=\"font-size:20px;color:green;\">"
                + "Username: <span style=\"font-weight: bold;\">" + S.getUsername() + "</span>"
                + "</p>"
                + "<p style=\"font-size:20px;color:green;\">"
                + "Email Id: <span style=\"font-weight: bold;\">" + S.getEmail() + "</span>"
                + "</p>"
                + "<p style=\"font-size:20px;color:green;\">"
                + "Mobile Number: <span style=\"font-weight: bold;\">" + S.getMobile() + "</span>"
                + "</p>";

        // Set the HTML content as the message text
        helper.setText(htmlContent,true);

        // Send the email
        javaMailSender.send(message);	
	}


}


