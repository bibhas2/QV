package com.mobiarch.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

public class ArtArtist {
	int artId;
	int artistId;
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
	
}
