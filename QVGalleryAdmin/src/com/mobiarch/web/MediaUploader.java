package com.mobiarch.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobiarch.entity.Media;
import com.mobiarch.model.MediaManager;

/**
 * Servlet implementation class MediaUploader
 */
@WebServlet("/MediaUploader")
public class MediaUploader extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	UserSession session;

	@EJB
    MediaManager mm;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (session.getGallery() == null) {
			throw new IllegalAccessError("Invalid session");
		}
		
		Media m = new Media();
		
		m.setFileName(request.getParameter("fileName"));
		m.setMimeType(request.getParameter("mimeType"));
		m.setLabel(request.getParameter("label"));
		m.setGalleryId(session.getGallery().getId());
		
		mm.addMedia(m, request.getInputStream());
	}

}
