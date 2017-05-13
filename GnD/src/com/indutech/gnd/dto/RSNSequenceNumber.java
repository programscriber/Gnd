package com.indutech.gnd.dto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "RSN_SEQUENCE_T")
public class RSNSequenceNumber {

	@Id
	@Column(name = "RSN_SEQUENCE_ID", unique = true, nullable = false, length=20)
	@GeneratedValue(strategy=GenerationType.AUTO)
	  private Long rsnSequenceId;

	public Long getRsnSequenceId() {
		return rsnSequenceId;
	}

	public void setRsnSequenceId(Long rsnSequenceId) {
		this.rsnSequenceId = rsnSequenceId;
	}
}
