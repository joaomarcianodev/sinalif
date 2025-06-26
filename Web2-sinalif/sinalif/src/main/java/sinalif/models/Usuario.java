package sinalif.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
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

	@Column(name = "data_criacao")
	private LocalDate data_criacao;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "perfil", joinColumns = @JoinColumn(name = "id_usuario"))
	@Column(name = "perfil_usuario")
	private List<String> perfis;

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
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

	public LocalDate getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(LocalDate data_criacao) {
		this.data_criacao = data_criacao;
	}

	public List<String> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<String> perfis) {
		this.perfis = perfis;
	}
}
