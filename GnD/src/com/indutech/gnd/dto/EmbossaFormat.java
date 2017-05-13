package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMBOSSA_VIP_T")
public class EmbossaFormat {
	
	@Id
	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="fORMAT_ID")
	private Long formatId;
	
	@Column(name="FIELD_NAME")
	private String fieldName;
	
	@Column(name="FIELD_LOCATION")
	private Integer fieldLocation;
	
	@Column(name="LENGTH")
	private Integer length;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFormatId() {
		return formatId;
	}

	public void setFormatId(Long formatId) {
		this.formatId = formatId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getFieldLocation() {
		return fieldLocation;
	}

	public void setFieldLocation(Integer fieldLocation) {
		this.fieldLocation = fieldLocation;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

}
