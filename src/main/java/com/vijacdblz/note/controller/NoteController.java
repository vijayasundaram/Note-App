package com.vijacdblz.note.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/notes/")
public class NoteController {
	@RequestMapping(method=RequestMethod.GET)
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

}
