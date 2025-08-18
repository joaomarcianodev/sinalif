package sinalif.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
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

	@Column(name = "data_ultima_execucao")
	private Instant dataUltimaExecucao;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
