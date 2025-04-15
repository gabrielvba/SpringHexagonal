package com.github.gabrielvba.ms_order_management.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@MappedSuperclass
public abstract class BaseEntity {
//public abstract class BaseEntity  implements Serializable {

//	@Id
//	@Temporal(TemporalType.TIMESTAMP)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_GENERATOR")
//    private Long  id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE", updatable = false)
	private LocalDateTime creation_date;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE")
	private LocalDateTime update_date;

	@PrePersist
	public void onCreate() {
		LocalDateTime now = LocalDateTime.now(); // Pega o horário atual
		setCreation_date(now); // Seta a data de criação
		setUpdate_date(now); // Seta a data de última atualização
	}

	@PreUpdate
	public void onUpdate() {
		setUpdate_date(LocalDateTime.now()); // Atualiza a data de última atualização
	}

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	public LocalDateTime getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(LocalDateTime update_date) {
		this.update_date = update_date;
	}

}
