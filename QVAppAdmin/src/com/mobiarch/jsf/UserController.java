package com.mobiarch.jsf;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import com.mobiarch.entity.Gallery;
import com.mobiarch.entity.User;
import com.mobiarch.model.SiteAdmin;
import java.util.List;

@ManagedBean(name="userCtrl")
public class UserController {
	Logger logger = Logger.getLogger(getClass().getName());
	User user = new User();
	@EJB
	SiteAdmin siteAdmin;
	String message;
	
	public String addUser() {
		logger.info("Adding user: " + user.getEmail());
		try {
			siteAdmin.addUser(user);
			message = "User was added successfully";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	public String testLogin() {
		User u = siteAdmin.authenticateUser(user.getEmail(), user.getPasswordString());
		if (u != null)
			message = "User was authenticated in successfully";
		else
			message = "Log in failed.";
		
		return null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<Gallery> getGalleryList() {
		return siteAdmin.getAllGalleries();
	}
	
}
