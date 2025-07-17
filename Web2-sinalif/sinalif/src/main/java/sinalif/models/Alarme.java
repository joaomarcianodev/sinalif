package sinalif.models;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "alarme")
@EntityListeners(AuditingEntityListener.class)
public class Alarme {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_alarme")
	private Long idAlarme;

	@NotNull(message = "O horário programado é um campo obrigatório")
	@Column(name = "horario_programado", nullable = false)
	private LocalTime horarioProgramado;


	@Column(name = "dias_semana", nullable = false)
	private String diasSemana;

	@Transient
	@NotEmpty(message= "Pelo menos um dia da semana deve ser selecionado.")
	private List<String> diasSemanaCheckbox;

	@NotNull(message= "Status Inicial é um campo obrigatório")
	@Column(name = "ativo")
	private boolean ativo = true;

	@Column(name = "pausado")
	private boolean pausado = false;

	@CreatedDate
	@Column(name = "data_criacao", updatable = false)
	private LocalDateTime dataCriacao;

	@LastModifiedDate
	@Column(name = "data_modificacao")
	private LocalDateTime dataModificacao;

	@NotNull(message = "A etiqueta é um campo obrigatório")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_etiqueta")
	private Etiqueta etiqueta;
}
