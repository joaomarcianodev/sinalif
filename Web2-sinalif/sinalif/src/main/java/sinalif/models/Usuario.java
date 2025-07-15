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
@Table(name = "usuarios")
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Usar GenerationType.IDENTITY para autoincremento
	@Column(name = "idUsuario")
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
	private String url_foto_perfil = "Sem foto";

	@Column(name = "notificacoes_ativas")
	private boolean notificacoes_ativas = false;

	@CreatedDate
	@Column(name = "data_criacao", updatable = false)
	private LocalDateTime data_criacao;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_perfis", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_perfil"))
	private List<Perfil> roles;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Sugestao> sugestoes;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Musica> musicas;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl_foto_perfil() {
		return url_foto_perfil;
	}

	public void setUrl_foto_perfil(String url_foto_perfil) {
		this.url_foto_perfil = url_foto_perfil;
	}

	public boolean isNotificacoes_ativas() {
		return notificacoes_ativas;
	}

	public void setNotificacoes_ativas(boolean notificacoes_ativas) {
		this.notificacoes_ativas = notificacoes_ativas;
	}

	public LocalDateTime getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(LocalDateTime data_criacao) {
		this.data_criacao = data_criacao;
	}

	public List<Perfil> getRoles() {
		return roles;
	}

	public void setRoles(List<Perfil> roles) {
		this.roles = roles;
	}

	public Set<Sugestao> getSugestoes() {
		return sugestoes;
	}

	public void setSugestoes(Set<Sugestao> sugestoes) {
		this.sugestoes = sugestoes;
	}

	public Set<Musica> getMusicas() {
		return musicas;
	}

	public void setMusicas(Set<Musica> musicas) {
		this.musicas = musicas;
	}
}