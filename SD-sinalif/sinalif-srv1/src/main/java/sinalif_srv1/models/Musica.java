package sinalif_srv1.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "musica")
@EntityListeners(AuditingEntityListener.class)
public class Musica {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id_musica;

    @NotNull(message= "Este campo é obrigatório")
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "status", nullable = true)
    private String status = "Pendente";

    @CreatedDate
    @Column(name = "data_criacao", nullable = true)
    private LocalDateTime data_criacao;

    @OneToMany(mappedBy = "musica", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<LogReproducao> logs;

    public Long getId_musica() {
        return id_musica;
    }

    public void setId_musica(Long id_musica) {
        this.id_musica = id_musica;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDateTime data_criacao) {
        this.data_criacao = data_criacao;
    }
}
