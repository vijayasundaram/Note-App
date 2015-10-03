package com.vijacdblz.note.controller;



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
import com.vijacdblz.note.model.User;
import com.vijacdblz.note.utility.GAHelper;
import com.vijacdblz.note.utility.PMHelper;



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

	

