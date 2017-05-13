package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FOLDER_PATHS")
public class FolderPaths {
	
	@Id
	@Column(name="PATH_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long pathId;
	
	@Column(name="PATH_TYPE")
	private String pathType;
	
	@Column(name="PATH")
	private String path;

	public final Long getPathId() {
		return pathId;
	}

	public final void setPathId(Long pathId) {
		this.pathId = pathId;
	}

	public final String getPathType() {
		return pathType;
	}

	public final void setPathType(String pathType) {
		this.pathType = pathType;
	}

	public final String getPath() {
		return path;
	}

	public final void setPath(String path) {
		this.path = path;
	}	

}
