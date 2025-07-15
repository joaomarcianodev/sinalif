package sinalif.models;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "alarme")
@EntityListeners(AuditingEntityListener.class)
public class Alarme {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id_alarme;

	@NotNull(message = "O horário programado é um campo obrigatório")
	@Column(nullable = false)
	private LocalTime horario_programado;

	@NotBlank(message= "Dias da Semana é um campo obrigatório")
	@Column(nullable = false)
	private String dias_semana;

	@NotNull(message= "Status Inicial é um campo obrigatório")
	@Column(nullable = true)
	private boolean ativo = true;

	@Column(nullable = true)
	private boolean pausado = false;

	@CreatedDate
	@Column(nullable = true, updatable = false)
	private LocalDateTime data_criacao;

	@LastModifiedDate
	@Column(nullable = true)
	private LocalDateTime data_modificacao;

	@NotNull(message = "A etiqueta é um campo obrigatório")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_etiqueta")
	private Etiqueta etiqueta;

	public long getId_alarme() {
		return id_alarme;
	}

	public void setId_alarme(long id_alarme) {
		this.id_alarme = id_alarme;
	}

	public LocalTime getHorario_programado() {
		return horario_programado;
	}

	public void setHorario_programado(LocalTime horario_programado) {
		this.horario_programado = horario_programado;
	}

	public String getDias_semana() {
		return dias_semana;
	}

	public void setDias_semana(String dias_semana) {
		this.dias_semana = dias_semana;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isPausado() {
		return pausado;
	}

	public void setPausado(boolean pausado) {
		this.pausado = pausado;
	}

	public LocalDateTime getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(LocalDateTime data_criacao) {
		this.data_criacao = data_criacao;
	}

	public LocalDateTime getData_modificacao() {
		return data_modificacao;
	}

	public void setData_modificacao(LocalDateTime data_modificacao) {
		this.data_modificacao = data_modificacao;
	}

	public Etiqueta getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(Etiqueta etiqueta) {
		this.etiqueta = etiqueta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
