package com.webage.jsf;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import com.mobiarch.entity.Art;
import com.mobiarch.entity.Artist;
import com.mobiarch.entity.Gallery;
import com.mobiarch.model.SiteAdmin;

@ManagedBean
public class Controller {
	Art art;
	Artist artist;
	int artId;
	int artistId;
	Gallery gallery;
	List<Art> artistWorkList;
	@EJB
	SiteAdmin siteAdmin;

	public Art getArt() {
		return art;
	}

	public Gallery getGallery() {
		return gallery;
	}

	public int getArtId() {
		return artId;
	}

	public void setArtId(int artId) {
		this.artId = artId;
	}

	public int getArtistId() {
		return artistId;
	}

	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}

	public Artist getArtist() {
		return artist;
	}

	public List<Art> getArtistWorkList() {
		return artistWorkList;
	}

	public void loadArt() {
		art = siteAdmin.getArtFull(artId);
		gallery = siteAdmin.getGallery(art.getGalleryId());
		
	}
	public void loadArtist() {
		artist = siteAdmin.getArtist(artistId);
		artistWorkList = siteAdmin.getAllArtForArtist(artistId);
	}
}
