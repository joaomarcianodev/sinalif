package sinalif.models;

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

    //private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id_musica;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotBlank(message= "URL é um campo obrigatório")
    @Column(name = "url", nullable = false)
    private String url;

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

    public LocalDateTime getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDateTime data_criacao) {
        this.data_criacao = data_criacao;
    }

    public void setId_musica(long id_musica) {
        this.id_musica = id_musica;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<LogReproducao> getLogs() {
        return logs;
    }

    public void setLogs(List<LogReproducao> logs) {
        this.logs = logs;
    }
}
