package com.mobiarch.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SysParam {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	Integer intValue;
	String stringValue;
	String name;
	String label;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getIntValue() {
		return intValue;
	}
	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
