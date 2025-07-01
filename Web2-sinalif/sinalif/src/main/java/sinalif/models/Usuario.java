package sinalif.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "usuarios")
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

	@Id
	@GeneratedValue
	@Column(name = "id_usuario")
	private Integer id_usuario;

	@Column(name = "nome")
	private String nome;

	@Column(name = "senha")
	private String senha;

	@Column(name = "email")
	private String email;

	@CreatedDate
	@Column(name = "data_criacao")
	private LocalDateTime data_criacao;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "Perfil", joinColumns = @JoinColumn(name = "id_usuario"))
	@Column(name = "perfil")
	private List<String> roles;
}