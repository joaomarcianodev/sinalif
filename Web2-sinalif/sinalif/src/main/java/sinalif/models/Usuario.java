package sinalif.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
	private Long id_usuario;

	@NotBlank(message= "Nome é um campo obrigatório")
	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "senha", nullable = false)
	private String senha;

	@NotBlank(message= "Email é um campo obrigatório")
	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "url_foto_perfil")
	private String url_foto_perfil = "Sem foto";

	@Column(name = "notificacoes_ativas")
	private boolean notificacoes_ativas = false;

	@CreatedDate
	@Column(name = "data_criacao", updatable = false)
	private LocalDateTime data_criacao;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_perfis", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_perfil"))
	private List<Perfil> roles;
}