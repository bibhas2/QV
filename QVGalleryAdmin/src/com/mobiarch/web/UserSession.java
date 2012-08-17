package com.mobiarch.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.mobiarch.entity.Gallery;
import com.mobiarch.entity.User;

@Named
@SessionScoped
public class UserSession implements Serializable {
	User user;
	Gallery gallery;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Gallery getGallery() {
		return gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}
}
