package sinalif_web.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "LogReproducao")
@EntityListeners(AuditingEntityListener.class)
public class LogReproducao {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id_logReproducao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_musica")
    private Musica musica;

    @CreatedDate
    @Column(name = "data_reproducao")
    private LocalDateTime data_reproducao;

    public long getId_logReproducao() {
        return id_logReproducao;
    }

    public void setId_logReproducao(long id_logReproducao) {
        this.id_logReproducao = id_logReproducao;
    }

    public Musica getMusica() {
        return musica;
    }

    public void setMusica(Musica musica) {
        this.musica = musica;
    }

    public LocalDateTime getData_reproducao() {
        return data_reproducao;
    }

    public void setData_reproducao(LocalDateTime data_reproducao) {
        this.data_reproducao = data_reproducao;
    }
}