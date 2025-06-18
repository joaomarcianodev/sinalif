package sinalif_srv3.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pausaProgramada")
public class PausaProgramada {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id_pausa;
	
	@Column(nullable = false)
	private LocalDate data_inicio;
	
	@Column(nullable = false)
	private LocalDate data_fim;
	
	@Column(nullable = false)
	private LocalTime hora_inicio;
	
	@Column(nullable = false)
	private LocalTime hora_fim;
	
	@Column(nullable = false)
	private boolean ativo;
	
	@Column(nullable = true)
	private LocalDate data_criacao;

	public long getId_pausa() {
		return id_pausa;
	}

	public void setId_pausa(long id_pausa) {
		this.id_pausa = id_pausa;
	}

	public LocalDate getData_inicio() {
		return data_inicio;
	}

	public void setData_inicio(LocalDate data_inicio) {
		this.data_inicio = data_inicio;
	}

	public LocalDate getData_fim() {
		return data_fim;
	}

	public void setData_fim(LocalDate data_fim) {
		this.data_fim = data_fim;
	}

	public LocalTime getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(LocalTime hora_inicio) {
		this.hora_inicio = hora_inicio;
	}

	public LocalTime getHora_fim() {
		return hora_fim;
	}

	public void setHora_fim(LocalTime hora_fim) {
		this.hora_fim = hora_fim;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDate getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(LocalDate data_criacao) {
		this.data_criacao = data_criacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
