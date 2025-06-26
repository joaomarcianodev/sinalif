package sinalif_srv3.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "etiqueta")
public class Etiqueta {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id_etiqueta;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Integer duracao;

	@OneToMany(mappedBy = "etiqueta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Alarme> alarmes;

	public long getId_etiqueta() {
		return id_etiqueta;
	}

	public void setId_etiqueta(long id_etiqueta) {
		this.id_etiqueta = id_etiqueta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}
}
