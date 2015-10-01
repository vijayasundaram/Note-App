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
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLoginPage() {
	        return "user/login";
	}
	
	
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
			
		ModelAndView view=new ModelAndView("redirect:/notes/");
		return view;
		}
		ModelAndView view=new ModelAndView("redirect:/login");
		return view;
	}
}	

	

