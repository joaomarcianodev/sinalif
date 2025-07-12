package sinalif.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "perfil")
public class Perfil implements GrantedAuthority {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id_perfil;

	@NotBlank(message= "Nome é um campo obrigatório")
	@Column(nullable = false)
	private String nome;

	public long getId_perfil() {
		return id_perfil;
	}

	public void setId_perfil(long id_perfil) {
		this.id_perfil = id_perfil;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getAuthority() {
		return this.nome;
	}
}
