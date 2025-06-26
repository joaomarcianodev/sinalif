package sinalif.models;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "LogReproducao")
@EntityListeners(AuditingEntityListener.class)
public class LogReproducao {

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
