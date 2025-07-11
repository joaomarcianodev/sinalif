package sinalif.models;

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
	private String duracao;

	@OneToMany(mappedBy = "etiqueta", cascade = CascadeType.ALL)
	private List<Alarme> alarmes;

	public List<Alarme> getAlarmes() {
		return alarmes;
	}

	public void setAlarmes(List<Alarme> alarmes) {
		this.alarmes = alarmes;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

		
}
