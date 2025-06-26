package sinalif_web.models;

import java.time.LocalDate; 
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType; 
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "usuario") 
public class Usuario {

    @Id
    @GeneratedValue
    @Column(name = "usuario_id") 
    private Integer id;

    @Column(name = "nome_usuario") 
    private String nome;

    @Column(name = "senha_usuario") 
    private String senha;

    @Column(name = "email_usuario") 
    private String email;

    @Column(name = "data_criacao") 
    private LocalDate data_criacao;

    @Enumerated(EnumType.STRING) 
    @Column(name = "perfil_usuario") 
    private Perfil perfil;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "usuario_id")) 
    @Column(name = "usuario_role")
    private List<String> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
    
    
}
