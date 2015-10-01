package com.vijacdblz.note.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class User {
	@PrimaryKey
	private String Id;
	@Persistent
	private String name;
	@Persistent
	private Date created;
	@Persistent
	private String picture;
	@Persistent
	private String email;
	@Persistent
    private List<Note> userNotes = new ArrayList<Note>();
	
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Note> getUserNotes() {
		return userNotes;
	}
	public void setUserNotes(List<Note> userNotes) {
		this.userNotes = userNotes;
	}
}
