package sinalif.models;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "pausa_programada")
@EntityListeners(AuditingEntityListener.class)
public class PausaProgramada {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pausa")
	private Long idPausa;

	@NotNull(message= "Data de início é um campo obrigatório")
	@FutureOrPresent(message = "A data de início deve ser no presente ou futuro.")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(name = "data_hora_inicio", nullable = false)
	private LocalDateTime dataHoraInicio;

	@NotNull(message= "Data de término é um campo obrigatório")
	@FutureOrPresent(message = "A data de término deve ser no presente ou futuro.")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(name = "data_hora_fim", nullable = false)
	private LocalDateTime dataHoraFim;

	@NotNull(message= "Status Inicial é um campo obrigatório")
	@Column(name = "ativo", nullable = false)
	private boolean ativo;

	@CreatedDate
	@Column(name = "data_cricao", updatable = false)
	private LocalDateTime dataCriacao;
}
