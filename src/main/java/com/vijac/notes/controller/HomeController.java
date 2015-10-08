package com.vijac.notes.controller;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.vijac.notes.model.User;
import com.vijac.notes.utility.GAHelper;
import com.vijac.notes.utility.PMHelper;




/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	/*NOTEAPP ROOT DISPLAY A LOGIN PAGE*/
	@RequestMapping(value = "/login/", method = RequestMethod.GET)
	public String showLoginPage() {
	        return "user/login";
	}
	
	
	
	
	/*OAUTH CALLBACK URL CREATE AN USER SET SESSION, ADD USER TO DATASTORE*/ 
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/oauth2callback/", method = RequestMethod.GET)
	public ModelAndView showHome(@RequestParam("code") String authcode,HttpSession session) throws IOException {
		
		Map<String, String> userInfo = new HashMap<String, String>();
		logger.info(authcode);
		if(authcode!=null){
			GAHelper gh = new GAHelper();
			userInfo = gh.getUserInfoJson(authcode);
			logger.info(userInfo.toString());
			session.setAttribute("name", userInfo.get("name"));
			session.setAttribute("email", userInfo.get("email"));
			session.setAttribute("picture", userInfo.get("picture"));
			session.setAttribute("id", userInfo.get("id"));
			
			String email = userInfo.get("email");
			
			
			//Persisting user if user doesn't exist already
			PersistenceManager pm = PMHelper.get().getPersistenceManager();
			List<User> results = null;
			Query q = pm.newQuery(User.class);
			q.setFilter("email =='"+email+"'");
			try {
				results = (List<User>) q.execute();
				if (results.isEmpty()) {
					logger.error("Creating a new user");
					User user = new User();
					user.setId(userInfo.get("id"));
					user.setName(userInfo.get("name"));
					user.setEmail(userInfo.get("email"));
					user.setPicture(userInfo.get("picture"));
					user.setCreated(new Date());
					pm.makePersistent(user);
					
					
					//Send a wecome email
					String msgBody = "Welcome to sunday samayal";
					Properties props = new Properties();
					Session sessionMail = Session.getDefaultInstance(props, null);
					try {
					    Message msg = new MimeMessage(sessionMail);
					    msg.setFrom(new InternetAddress("vijaya@a-cti.com", "VijacNotes Admin"));
					    msg.addRecipient(Message.RecipientType.TO,
					     new InternetAddress(email, userInfo.get("name")));
					    msg.setSubject("Your Notes account has been created successfully");
					    msg.setText(msgBody);
					    Transport.send(msg);

					} catch (AddressException e) {
					    // ...
					} catch (MessagingException e) {
					    // ...
					}
					
					
					
				}
				else{
					logger.info("User exist already:)");
				}
					
			} finally {
				q.closeAll();
				pm.close();
			}
			
			ModelAndView view=new ModelAndView("redirect:/");
			return view;
		}
		else{
			ModelAndView view=new ModelAndView("redirect:/login");
			return view;
		}
	}
	
	/*DISPLAY THE APP'S HOME PAGE*/
	@RequestMapping(value = "/",method=RequestMethod.GET)
	public String showNotes(HttpSession session,Model m){
		String name = (String) session.getAttribute("name");
		String email = (String) session.getAttribute("email");
		String picture = (String) session.getAttribute("picture");
		String id =  (String) session.getAttribute("id");
		m.addAttribute("username", name);
		m.addAttribute("email", email);
		m.addAttribute("picture", picture);
		m.addAttribute("id", id);
		
		
		return "note/index";
	}
	
	
	
	
	
	/*LOG OUT METHOD*/
	@RequestMapping(value="/logout/")
	public String logoutUser(HttpSession session){
		session.invalidate();
		return "user/login";
	}
	
	
}	
