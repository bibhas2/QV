package com.mobiarch.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Art {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	String name;
	String description;
	String thumbnail;
	int galleryId;
	
	@Transient
	private List<ArtMedia> mediaList;
	
	public List<ArtMedia> getMediaList() {
		return mediaList;
	}
	public void setMediaList(List<ArtMedia> media) {
		this.mediaList = media;
	}
	
	@Transient
	List<Artist> artistList;
	
	public List<Artist> getArtistList() {
		return artistList;
	}
	public void setArtistList(List<Artist> artistList) {
		this.artistList = artistList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getGalleryId() {
		return galleryId;
	}
	public void setGalleryId(int galleryId) {
		this.galleryId = galleryId;
	}
}
