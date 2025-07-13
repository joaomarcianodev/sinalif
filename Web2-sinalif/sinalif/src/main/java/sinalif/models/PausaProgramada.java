package sinalif.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pausaProgramada")
@EntityListeners(AuditingEntityListener.class)
public class PausaProgramada {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id_pausa;

	@NotNull(message= "Data de início é um campo obrigatório")
	@FutureOrPresent(message = "A data de início deve ser no presente ou futuro.")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(nullable = false)
	private LocalDateTime data_hora_inicio;

	@NotNull(message= "Data de término é um campo obrigatório")
	@FutureOrPresent(message = "A data de término deve ser no presente ou futuro.")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(nullable = false)
	private LocalDateTime data_hora_fim;

	@NotNull(message= "Status Inicial é um campo obrigatório")
	@Column(nullable = false)
	private boolean ativo;

	@CreatedDate
	@Column(nullable = true, updatable = false)
	private LocalDateTime data_criacao;

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

	public LocalDateTime getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(LocalDateTime data_criacao) {
		this.data_criacao = data_criacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
