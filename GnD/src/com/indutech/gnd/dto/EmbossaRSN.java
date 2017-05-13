package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMBOSSA_RSN_T")
public class EmbossaRSN {
	
	@Id
	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="FORMAT_ID")
	private Integer formatId;
	
	@Column(name="RSN_FROM")
	private Integer rsnFrom;
	
	@Column(name="RSN_TO")
	private Integer rsnTo;
	
	@Column(name="EMB_FORMAT")
	private Integer embossaFormat;
	
	

	public Integer getEmbossaFormat() {
		return embossaFormat;
	}

	public void setEmbossaFormat(Integer embossaFormat) {
		this.embossaFormat = embossaFormat;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getFormatId() {
		return formatId;
	}

	public void setFormatId(Integer formatId) {
		this.formatId = formatId;
	}

	public Integer getRsnFrom() {
		return rsnFrom;
	}

	public void setRsnFrom(Integer rsnFrom) {
		this.rsnFrom = rsnFrom;
	}

	public Integer getRsnTo() {
		return rsnTo;
	}

	public void setRsnTo(Integer rsnTo) {
		this.rsnTo = rsnTo;
	}

	
}
