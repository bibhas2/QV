package com.mobiarch.model;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mobiarch.entity.SysParam;

@Stateless
public class ParamManager {
	@PersistenceContext(name="QVPU")
	EntityManager em;
	Logger logger = Logger.getLogger(getClass().getName());

	public void updateParam(SysParam p) {
		SysParam old = em.find(SysParam.class, p.getId());
		
		old.setIntValue(p.getIntValue());
		old.setStringValue(p.getStringValue());
	}
	
	public int getIntValue(String name) {
		SysParam p = findByName(name);
		
		return p.getIntValue();
	}
	
	public String getStringValue(String name) {
		return findByName(name).getStringValue();
	}

	public SysParam findByName(String name) {
		TypedQuery<SysParam> q = em.createQuery("select s from SysParam s where name=:name", SysParam.class);
		
		q.setParameter("name", name);
		
		return q.getSingleResult();
	}
	
	public List<SysParam> getAll() {
		return em.createQuery("select s from SysParam s", SysParam.class).getResultList();
	}
}
