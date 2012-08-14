package com.mobiarch.jsf;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.mobiarch.entity.Art;
import com.mobiarch.entity.User;
import com.mobiarch.model.SiteAdmin;

@ManagedBean
public class Controller {
	User user = new User();
	Art art = new Art();
	@EJB
	SiteAdmin siteAdmin;
	@Inject
	UserSession session;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Art getArt() {
		return art;
	}

	public void setArt(Art art) {
		this.art = art;
	}

	public String login() {
		User u = siteAdmin.authenticateUser(user.getEmail(), user.getPasswordString());
		if (u == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", ""));
			return null;
		} else {
			user = u;
			session.setUser(user);
			session.setGallery(siteAdmin.getGallery(user.getGalleryId()));
		}
		return "home?faces-redirect=true";
	}
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "index?faces-redirect=true";
	}
	
	public void checkSession() {
		if (session == null || session.getUser() == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			NavigationHandler navigator = context.getApplication().getNavigationHandler();
			
			navigator.handleNavigation(context, null, "index?faces-redirect=true");
		}
	}
	
	public void loadArt() {
		art = siteAdmin.getArt(art.getId());
	}
}
