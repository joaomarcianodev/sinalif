package sinalif.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "usuario")
@EntityListeners(AuditingEntityListener.class)
public class Usuario {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Usar GenerationType.IDENTITY para autoincremento
	@Column(name = "id_usuario")
	private Long idUsuario;

	@NotBlank(message= "Nome é um campo obrigatório")
	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "senha", nullable = false)
	private String senha;

	@NotBlank(message= "Email é um campo obrigatório")
	@Column(name = "email", nullable = false, unique = true) // Adicionado unique = true para o email
	private String email;

	@Column(name = "url_foto_perfil")
	private String urlFotoPerfil = "Sem foto";

	@Column(name = "notificacoes_ativas")
	private boolean notificacoesAtivas = false;

	@CreatedDate
	@Column(name = "data_criacao", updatable = false)
	private LocalDateTime dataCriacao;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_perfis", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_perfil"))
	private List<Perfil> roles;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Sugestao> sugestoes;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Musica> musicas;
}