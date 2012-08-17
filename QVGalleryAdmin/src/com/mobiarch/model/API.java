package com.mobiarch.model;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mobiarch.entity.Art;
import com.mobiarch.entity.ArtMedia;
import com.mobiarch.entity.Artist;
import com.mobiarch.entity.Media;
import com.mobiarch.web.UserSession;

@Path("/api")
@Stateless
public class API {
	@EJB
	SiteAdmin siteAdmin;
	@EJB
	MediaManager mediaManager;
	
	@Inject
	UserSession session;
	
	Logger logger = Logger.getLogger(getClass().getName());


	private void checkSession() throws IllegalAccessError {
		if (session == null || session.getGallery() == null) {
			throw new IllegalAccessError("Invalid session");
		}
	}
	
	@PUT
	@Path("/arts")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addArt(Art art) {
		checkSession();

		logger.info(art.getName() + "," + art.getThumbnail());
		art.setGalleryId(session.getGallery().getId());
		siteAdmin.addArt(art);
	}

	@POST
	@Path("/arts/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateArt(@PathParam("id") int id, Art art) {
		checkSession();

		art.setId(id);
		
		Art old = siteAdmin.getArt(id);
		//Does this art belong to user's gallery?
		if (old.getGalleryId() != session.getGallery().getId()) {
			throw new IllegalAccessError();
		}

		siteAdmin.updateArt(art);
	}

	@PUT
	@Path("/artmedias")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addArtMedia(ArtMedia artMedia) {
		checkSession();

		siteAdmin.addArtMedia(artMedia);
	}
	
	@PUT
	@Path("/arts/{id}/artists")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addArtistToArt(@PathParam("id") int artId, Artist artist) {
		checkSession();

		artist.setGalleryId(session.getGallery().getId());
		siteAdmin.addArtistToArt(artist, artId);
	}

	@POST
	@Path("/artists/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateArtist(@PathParam("id") int id, Artist artist) {
		checkSession();

		artist.setId(id);
		
		Artist old = siteAdmin.getArtist(id);
		//Does this artist belong to user's gallery?
		if (old.getGalleryId() != session.getGallery().getId()) {
			throw new IllegalAccessError();
		}

		siteAdmin.updateArtist(artist);
	}

	@PUT
	@Path("/arts/{id}/artists/{artistId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addArtistToArt(@PathParam("id") int artId, @PathParam("artistId") int artistId) {
		checkSession();

		siteAdmin.addArtistToArt(artistId, artId);
	}

	@DELETE
	@Path("/artmedias/{id}")
	public void deleteArtMedia(@PathParam("id") int id) {
		checkSession();
		//TODO: Security check
		siteAdmin.deleteArtMedia(id);
	}
	
	@DELETE
	@Path("/arts/{artId}/artists/{artistId}")
	public void deleteArtistFromArt(@PathParam("artId") int artId,
			@PathParam("artistId") int artistId) {
		checkSession();
		//TODO: Security check
		siteAdmin.deleteArtistFromArt(artistId, artId);
	}

	@GET
	@Path("/medias")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Media> getMedias() {
		checkSession();
		return mediaManager.getAllForGallery(session.getGallery().getId());
	}

	@DELETE
	@Path("/medias/{id}")
	public void deleteMedia(@PathParam("id") int mediaId) {
		checkSession();
		//TODO: Security check
		mediaManager.deleteMedia(mediaId);
	}

	@GET
	@Path("/arts")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Art> getArts() {
		checkSession();
		return siteAdmin.getAllArt(session.getGallery().getId());
	}

	@GET
	@Path("/arts/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Art getArt(@PathParam("id") int id) {
		checkSession();
		Art a = siteAdmin.getArtFull(id);
		
		if (a.getGalleryId() != session.getGallery().getId()) {
			throw new IllegalAccessError("Invalid session");
		}
		
		return a;
	}

	@GET
	@Path("/artists")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Artist> getAllArtists() {
		checkSession();
		return siteAdmin.getAllArtists(session.getGallery().getId());
	}

}
