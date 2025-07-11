package sinalif.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pausaProgramada")
public class PausaProgramada {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id_pausa;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(nullable = false)
	private LocalDateTime data_hora_inicio;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(nullable = false)
	private LocalDateTime data_hora_fim;

	@NotNull(message= "Status Inicial é um campo obrigatório")
	@Column(nullable = false)
	private boolean ativo;
	
	@Column(nullable = false)
	private LocalDate data_criacao;

	public long getId_pausa() {
		return id_pausa;
	}

	public void setId_pausa(long id_pausa) {
		this.id_pausa = id_pausa;
	}

	public LocalDateTime getData_hora_inicio() {
		return data_hora_inicio;
	}

	public void setData_hora_inicio(LocalDateTime data_hora_inicio) {
		this.data_hora_inicio = data_hora_inicio;
	}

	public LocalDateTime getData_hora_fim() {
		return data_hora_fim;
	}

	public void setData_hora_fim(LocalDateTime data_hora_fim) {
		this.data_hora_fim = data_hora_fim;
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
