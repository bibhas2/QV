package com.mobiarch.jsf;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import com.mobiarch.entity.SysParam;
import com.mobiarch.model.ParamManager;

@ManagedBean
public class Controller {
	@EJB
	ParamManager pm;
	List<SysParam> paramList;
	SysParam param = new SysParam();
	
	public void loadParamList() {
		paramList = pm.getAll();
	}
	
	public void loadParam() {
		param = pm.findByName(param.getName());
	}

	public String updateParam() {
		pm.updateParam(param);
		
		return "param_list?faces-redirect=true";
	}
	
	public List<SysParam> getParamList() {
		return paramList;
	}

	public SysParam getParam() {
		return param;
	}

	public void setParam(SysParam param) {
		this.param = param;
	}
}
