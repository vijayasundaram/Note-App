package com.vijacdblz.note.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.vijacdblz.note.model.Note;
import com.vijacdblz.note.model.User;
import com.vijacdblz.note.utility.PMHelper;

@Controller
@RequestMapping(value="/note/")
public class NoteController {
	
	
	private static final Logger log = LoggerFactory.getLogger(NoteController.class);
	
	/*-----------------SHOW NOTES PAGE-----------------*/
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody List<Note> listUserNotes(HttpSession session){
		String email = (String) session.getAttribute("email");
		
		//Get every notes from current user
		PersistenceManager pm = PMHelper.get().getPersistenceManager();
		Query q = pm.newQuery(User.class, "email == value");
		q.declareParameters("String value");
	    List<User> results = (List<User>) q.execute(email);
	    Iterator iter = results.iterator();
	    User currentUser = null;
	    while (iter.hasNext())
	    {
	         currentUser = (User)iter.next();
	    }
	    
		return currentUser.getUserNotes();
	}
	
	/*-------------ADD NEW NOTE FROM POST----------------*/
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Note addNote(@RequestParam("title") String title, @RequestParam("content") String content,HttpSession session){
		log.info("INsisde note controller");
		Note note = new Note();
		note.setContent(content);
		note.setDate(new Date());
		note.setTitle(title);
		log.info(note.toString());
		
		
		
		//Get User, Append note to existing List<Note>
		String email = (String) session.getAttribute("email");
		
		PersistenceManager pm = PMHelper.get().getPersistenceManager();
		Query q = pm.newQuery(User.class, "email == value");
		q.declareParameters("String value");
	    List<User> results = (List<User>) q.execute(email);
	    Iterator iter = results.iterator();
	    User currentUser = null;
	    while (iter.hasNext())
	    {
	         currentUser = (User)iter.next();
	    }
	    currentUser.appendUserNotes(note);
		
	    try {
			pm.makePersistent(currentUser);
		} finally {
			pm.close();
		}
		
		
		
		return note;
		
	}
	
	
	
	
	

}
