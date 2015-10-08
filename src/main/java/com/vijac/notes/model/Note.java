package com.vijac.notes.model;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class Note {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
    @Persistent
    private String content;
	
    @Persistent
    private String title;
	
    @Persistent
    private Date created;
    
    @Persistent
    private Date updated;
    
	public String getKey() {
		return KeyFactory.keyToString(key);
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreatedDate() {
		return created;
	}

	public void setCreatedDate(Date date) {
		this.created = date;
	}
	
	public Date getUpdatedDate(){
		return updated;
	}
	
	public void setUpdatedDate(Date date){
		this.updated =  date;
	}

    
    
    
}