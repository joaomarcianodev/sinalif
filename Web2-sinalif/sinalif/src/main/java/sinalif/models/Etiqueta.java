package sinalif.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "etiqueta")
public class Etiqueta {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_etiqueta")
	private Long idEtiqueta;

	@NotBlank(message= "Nome é um campo obrigatório")
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotBlank(message= "Duração é um campo obrigatório")
	@Column(name = "duracao", nullable = false)
	private String duracao;

	@OneToMany(mappedBy = "etiqueta", cascade = CascadeType.ALL)
	private List<Alarme> alarmes;
}
