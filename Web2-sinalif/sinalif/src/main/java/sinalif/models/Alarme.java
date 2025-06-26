package sinalif.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alarme")
public class Alarme {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id_alarme;
	
	@Column(nullable = false)
	private LocalTime horario_programado;
	
	@Column(nullable = false)
	private String dias_semana;
	
	@Column(nullable = false)
	private boolean ativo;
	
	@Column(nullable = false)
	private boolean pausado;
	
	@Column(nullable = false)
	private LocalDate data_criacao;
	
	@Column(nullable = false)
	private LocalDate data_modificacao;
	
	@ManyToOne 
    @JoinColumn(name = "id_etiqueta", nullable = false) 
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

	public LocalDate getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(LocalDate data_criacao) {
		this.data_criacao = data_criacao;
	}

	public LocalDate getData_modificacao() {
		return data_modificacao;
	}

	public void setData_modificacao(LocalDate data_modificacao) {
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
