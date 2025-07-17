package sinalif.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "log_reproducao")
@EntityListeners(AuditingEntityListener.class)
public class LogReproducao {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log_reproducao")
    private Long idLogReproducao;

    @CreatedDate
    @Column(name = "data_reproducao")
    private LocalDateTime dataReproducao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_musica", nullable = false)
    private Musica musica;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LogReproducao that = (LogReproducao) o;
        return Objects.equals(idLogReproducao, that.idLogReproducao);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idLogReproducao);
    }
}
