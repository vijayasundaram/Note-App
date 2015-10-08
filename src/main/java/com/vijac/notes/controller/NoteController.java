package com.vijac.notes.controller;


import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.vijac.notes.model.Note;
import com.vijac.notes.model.User;
import com.vijac.notes.utility.PMHelper;

@Controller
@RequestMapping(value="/note/")
public class NoteController {
	
	
	private static final Logger log = LoggerFactory.getLogger(NoteController.class);
	
	/*-----------------SHOW NOTES PAGE-----------------*/
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody List<Note> listUserNotes(HttpSession session){
		String email = (String) session.getAttribute("email");
		
		//Get every notes from current user
		PersistenceManager pm = PMHelper.get().getPersistenceManager();
		Query q = pm.newQuery(User.class, "email == value");
		q.declareParameters("String value");
	    @SuppressWarnings("unchecked")
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
	@ResponseStatus(value = HttpStatus.OK)
	public void addNote(@RequestParam("title") String title, @RequestParam("content") String content,HttpSession session){
		log.info("Inside note controller");
		Note note = new Note();
		note.setContent(content);
		note.setCreatedDate(new Date());
		note.setTitle(title);
		log.info(note.toString());
		
		
		
		//Get User, Append note to existing List<Note>
		String email = (String) session.getAttribute("email");
		
		PersistenceManager pm = PMHelper.get().getPersistenceManager();
		Query q = pm.newQuery(User.class, "email == value");
		q.declareParameters("String value");
	    @SuppressWarnings("unchecked")
		List<User> results = (List<User>) q.execute(email);
	    @SuppressWarnings("rawtypes")
		Iterator iter = results.iterator();
	    User currentUser = null;
	    while (iter.hasNext())
	    {
	         currentUser = (User)iter.next();
	    }
	    currentUser.addNote(note);
		
	    try {
			pm.makePersistent(currentUser);
		} finally {
			pm.close();
		}
		
	}
	/*---------------- :GET note/:key----------------*/
	@RequestMapping(value="/{key}/", method=RequestMethod.GET)
	public @ResponseBody Note getNote(@PathVariable String key){
		
		PersistenceManager pm = PMHelper.get().getPersistenceManager();
		try {
			Note note = pm.getObjectById(Note.class,key);
			return note;
			
		} finally {
			pm.close();
		}
		
	}
	
	/*------------- :PUT note/:key----------------*/
	@RequestMapping(value="/{key}/", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void editNote(@PathVariable String key,@RequestParam("title") String title, @RequestParam("content") String content){
		
		PersistenceManager pm = PMHelper.get().getPersistenceManager();
		try {
			Note note = pm.getObjectById(Note.class,key);
			note.setContent(content);
			note.setTitle(title);
			note.setUpdatedDate(new Date());
			
		} finally {
			pm.close();
		}
		
	}
	
	
	/*------------- :DELETE note/:key----------------*/
	@RequestMapping(value="/{key}/", method=RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteNote(@PathVariable String key,HttpSession session){
		
		
		//Get User, Append note to existing List<Note>
		String email = (String) session.getAttribute("email");
		
		PersistenceManager pm = PMHelper.get().getPersistenceManager();
		Query q = pm.newQuery(User.class, "email == value");
		q.declareParameters("String value");
	    @SuppressWarnings("unchecked")
		List<User> results = (List<User>) q.execute(email);
	    @SuppressWarnings("rawtypes")
		Iterator iter = results.iterator();
	    User currentUser = null;
	    while (iter.hasNext())
	    {
	         currentUser = (User)iter.next();
	    }
	    currentUser.deleteNoteWithKey(key);

	    try {
			pm.makePersistent(currentUser);
		} finally {
			pm.close();
		}
		
		
		
	}
	
	

}
