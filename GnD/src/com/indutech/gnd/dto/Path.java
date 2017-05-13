package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FOLDER_PATHS")
public class Path {
	
	@Id
	@Column(name="PATH_ID", nullable = false, unique=true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="PATH_TYPE")
	private String type;
	@Column(name="PATH")
	private String path;
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final String getType() {
		return type;
	}
	public final void setType(String type) {
		this.type = type;
	}
	public final String getPath() {
		return path;
	}
	public final void setPath(String path) {
		this.path = path;
	}
	
	
	

}
