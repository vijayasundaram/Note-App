package com.vijac.notes.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


 
public class AuthenticationInterceptor implements HandlerInterceptor  {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);
	
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        	String userEmail = (String) request.getSession().getAttribute("email");

            //List of urls that can be accessed without authentication!

	        boolean urlFromRoot = (request.getRequestURI().equals("/login/")||
                    request.getRequestURI().equals("/login")||
                    request.getRequestURI().equals("/oauth2callback/")||
                    request.getRequestURI().equals("/oauth2callback")||
                    request.getRequestURI().equals("/account/register/")||
                    request.getRequestURI().equals("/account/register")
                                    );
	        
	        
	        if(urlFromRoot){
	        	log.info("we are in root urls: no auth needed");
	        	return true;
	        }
	        else if(userEmail== null){
	        	log.info("Authentication failed mate :(");
	        	log.info(request.getRequestURL().toString());
	        	response.sendRedirect("/login/");
	        	return false;
	        }
	        else{
	        	return true;
	        }
	    }
     
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    	String urlFrom = request.getRequestURI();
        log.info("Post Handler"+ urlFrom);
    }
     
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    	log.info("After Completion Handler");
    }
}