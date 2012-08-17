package com.mobiarch.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mobiarch.entity.Media;

@Stateless
public class MediaManager {
	@EJB
	ParamManager pm;
	
	@PersistenceContext(name="QVPU")
	EntityManager em;
	Logger logger = Logger.getLogger(getClass().getName());

	private String getDerivedFileName(Media m) {
		return "Media-" + m.getGalleryId() + "-" + m.getId() + "-" + m.getFileName();
	}

	public void addMedia(Media m, InputStream is) {
		em.persist(m);
		em.flush();
		
		m.setFileName(getDerivedFileName(m));
		File file = new File(pm.getStringValue("media_dir"), m.getFileName());
		logger.info("Writing to: " + file.getAbsoluteFile());
		logger.info("Gallery: " + m.getGalleryId());
		
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(file);
			byte buff[] = new byte[256];
			int len;
			
			while ((len = is.read(buff)) > 0) {
				os.write(buff, 0, len);
			}
			os.close();
		} catch (Exception e) {
			if (os != null) {
				try {
					os.close();
					file.delete();
				} catch (IOException e1) {
				}
			}
			throw new RuntimeException(e);
		}
	}
	
	public List<Media> getAllForGallery(int galleryId) {
		List<Media> list = em.createQuery("select m from Media m", Media.class).getResultList();
		String mediaURL = pm.getStringValue("media_url_prefix");
		
		for (Media m : list) {
			m.setUrl(mediaURL + "/" + m.getFileName());
		}
		
		return list;
	}
}