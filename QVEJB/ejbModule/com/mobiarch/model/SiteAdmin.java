package com.mobiarch.model;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.mobiarch.entity.Art;
import com.mobiarch.entity.ArtMedia;
import com.mobiarch.entity.Artist;
import com.mobiarch.entity.Gallery;
import com.mobiarch.entity.User;

@Stateless
public class SiteAdmin {
	@PersistenceContext(name="QVPU")
	EntityManager em;
	Logger logger = Logger.getLogger(getClass().getName());

	public void addUser(User u) throws Exception {
		u.setEmail(u.getEmail().toLowerCase());
		u.setPassword(getPasswordHash(u.getPasswordString()));
	       
		em.persist(u);
	}

	private byte[] getPasswordHash(String password) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
	    digest.reset();
	    byte[] hash = digest.digest(password.getBytes("UTF-8"));
	    
	    return hash;
	}
	
	public User authenticateUser(String email, String password) {
		try {
			byte hash[] = getPasswordHash(password);
			TypedQuery<User> q = em.createQuery("select u from User u where email=:email", User.class);
			
			q.setParameter("email", email.toLowerCase());
			
			User u = q.getSingleResult();
			
			if (Arrays.equals(hash, u.getPassword())) {
				return u;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Login failed", e);
		}
		return null;
	}
	
	public List<Gallery> getAllGalleries() {
		return em.createQuery("select g from Gallery g", Gallery.class).getResultList();
	}
	
	public Gallery getGallery(int id) {
		return em.find(Gallery.class, id);
	}
	
	public List<Art> getAllArt(int galleryId) {
		TypedQuery<Art> q = em.createQuery("select a from Art a where galleryid=:galleryid", Art.class);
		
		q.setParameter("galleryid", galleryId);
		
		List<Art> list = q.getResultList();
		
		for (Art a : list) {
			a.setMediaList(getAllMediaForArt(a.getId()));
			a.setArtistList(getAllArtistForArt(a.getId()));
		}
		
		return list;
	}

	public Art getArt(int id) {
		return em.find(Art.class, id);
	}

	public Art getArtFull(int id) {
		Art a = getArt(id);
		
		a.setMediaList(getAllMediaForArt(a.getId()));
		a.setArtistList(getAllArtistForArt(a.getId()));
		
		return a;
	}
	
	public List<ArtMedia> getAllMediaForArt(int artId) {
		TypedQuery<ArtMedia> q = em.createQuery("select a from ArtMedia a where artid=:artid", ArtMedia.class);
		
		q.setParameter("artid", artId);
		
		List<ArtMedia> list = q.getResultList();
		
		return list;
	}
	
	public ArtMedia getArtMedia(int id) {
		return em.find(ArtMedia.class, id);
	}
	
	public void updateArtMedia(ArtMedia a) {
		
	}
	
	public List<Artist> getAllArtistForArt(int artId) {
		Query q = em.createNativeQuery(
				"select * from Artist where id in (select artistid from art_artist where artid=?)", 
				Artist.class);
		
		q.setParameter(1, artId);
		
		List<Artist> list = (List<Artist>) q.getResultList();
		
		return list;
	}

	public List<Art> getAllArtForArtist(int artistId) {
		Query q = em.createNativeQuery(
				"select * from Art where id in (select artid from art_artist where artistid=?)", 
				Art.class);
		
		q.setParameter(1, artistId);
		
		List<Art> list = (List<Art>) q.getResultList();
		
		return list;
	}

	public void addArtMedia(ArtMedia am) {
		em.persist(am);
	}
	public void deleteArtMedia(int id) {
		ArtMedia am = em.find(ArtMedia.class, id);
		
		em.remove(am);
	}
	
	public Artist getArtist(int id) {
		return em.find(Artist.class, id);
	}
	
	public void addArtist(Artist a) {
		em.persist(a);
	}
	
	public List<Artist> getAllArtists(int galleryId) {
		TypedQuery<Artist> q = em.createQuery("select a from Artist a where galleryid=:galleryid", Artist.class);
		
		q.setParameter("galleryid", galleryId);
		
		List<Artist> list = q.getResultList();
		
		return list;
	}	
	
	public void addArtistToArt(Artist a, int artId) {
		em.persist(a);
		em.flush();
		
		addArtistToArt(a.getId(), artId);
	}
	public void addArt(Art a) {
		em.persist(a);
	}

	public void deleteArtistFromArt(int artistId, int artId) {
		Query q = em.createNativeQuery(
				"delete from art_artist where artid=? and artistid=?");
		q.setParameter(1, artId);
		q.setParameter(2, artistId);
		
		q.executeUpdate();
	}

	public void addArtistToArt(int artistId, int artId) {
		//See if the artist already exists
		Query q = em.createNativeQuery(
				"select * from art_artist where artid=? and artistid=?");
		q.setParameter(1, artId);
		q.setParameter(2, artistId);
		if (q.getResultList().size() > 0) {
			return;
		}
		

		q = em.createNativeQuery(
				"insert into art_artist (artid, artistid) values (?, ?)");
		q.setParameter(1, artId);
		q.setParameter(2, artistId);
		
		q.executeUpdate();
	}

	public void updateArtist(Artist update) {
		Artist old = getArtist(update.getId());
		
		old.setName(update.getName());
		old.setPhoto(update.getPhoto());
	}

	public void updateArt(Art art) {
		Art old = getArt(art.getId());
		
		old.setName(art.getName());
		old.setThumbnail(art.getThumbnail());
		old.setDescription(art.getDescription());
	}
}
