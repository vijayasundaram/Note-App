package com.vijac.notes.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.vijac.notes.model.User;
import com.vijac.notes.utility.PMHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value="/account/")
public class AccountController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	
	@RequestMapping(value="/sendthefuckingmail",method=RequestMethod.GET)
	@ResponseBody 
	@ResponseStatus(value = HttpStatus.OK)
	public void  sendGeneratedPassword() throws UnsupportedEncodingException{
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String msgBody = "...";

		try {
		    Message msg = new MimeMessage(session);
		    msg.setFrom(new InternetAddress("vijay.sundaram@a-cti.com", "Vijac Notes Admin"));
		    msg.addRecipient(Message.RecipientType.TO,
		     new InternetAddress("vijacdblz@gmail.com", "Mr. Vijayasundaram"));
		    msg.setSubject("Your Example.com account has been activated");
		    msg.setText(msgBody);
		    Transport.send(msg);
		    logger.info("anupichutean da venna");

		} catch (AddressException e) {
		    // ...
		} catch (MessagingException e) {
		    // ...
		}
        
        
	}
	@RequestMapping(value="/register", method = RequestMethod.GET)
    public String showRegisterVIew(){
        return "user/register";
    }
	@RequestMapping(value = "/register",method = RequestMethod.POST)
    public String sendConfirmationMail(@RequestParam("email") String email,@RequestParam("name") String name,Model model) throws UnsupportedEncodingException {
        //Generate a random String
        SecureRandom random = new SecureRandom();
        String randPassword = new BigInteger(130, random).toString(32);
        BigInteger randId = new BigInteger(21,random);
        logger.info(randPassword);
        logger.info(randId.toString());
        //Check if user already exist
        PersistenceManager pm = PMHelper.get().getPersistenceManager();
        List<User> results = null;
        Query q = pm.newQuery(User.class);
        q.setFilter("email =='"+email+"'");
        try {
            results = (List<User>) q.execute();
            if (results.isEmpty()) {
                logger.error("Creating new User");
                User user = new User();
                user.setId(randId.toString());
                user.setName(name);
                user.setEmail(email);
                user.setCreated(new Date());
                pm.makePersistent(user);


                //Send a welcome email

                String msgBody = "Welcome to Notes, you password is "+randPassword;
                Properties props = new Properties();
                Session sessionMail = Session.getDefaultInstance(props, null);
                try {
                    Message msg = new MimeMessage(sessionMail);
                    msg.setFrom(new InternetAddress("vijay.sundaram@a-cti.com", "Notes App"));
                    msg.addRecipient(Message.RecipientType.TO,
                            new InternetAddress("vijay.sundaram@a-cti.com", name));
                    msg.setSubject("Your Notes account has been created successfully");
                    msg.setText(msgBody);
                    Transport.send(msg);

                } catch (AddressException e) {
                    // ...
                } catch (MessagingException e) {
                    // ...
                }

                model.addAttribute("message","success");

            }
            else{
                logger.info("User exist already:)");
                model.addAttribute("message","error");
            }

        } finally {
            q.closeAll();
            pm.close();
        }

        model.addAttribute("email",email);
        return "user/login";
    }
	
}
