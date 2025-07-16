package sinalif.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sugestoes")
@EntityListeners(AuditingEntityListener.class)
public class Sugestao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sugestao")
    private Long id_sugestao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "status_sugestao")
    private String status_sugestao = "Pendente";

    @CreatedDate
    @Column(name = "data_sugestao", updatable = false)
    private LocalDateTime data_sugestao;

    @Column(name = "data_analise")
    private LocalDateTime data_analise = null;

    public Long getId_sugestao() {
        return id_sugestao;
    }

    public void setId_sugestao(Long id_sugestao) {
        this.id_sugestao = id_sugestao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUrl_sugerida() {
        return url;
    }

    public void setUrl_sugerida(String url) {
        this.url = url;
    }

    public String getStatus_sugestao() {
        return status_sugestao;
    }

    public void setStatus_sugestao(String status_sugestao) {
        this.status_sugestao = status_sugestao;
    }

    public LocalDateTime getData_sugestao() {
        return data_sugestao;
    }

    public void setData_sugestao(LocalDateTime data_sugestao) {
        this.data_sugestao = data_sugestao;
    }

    public LocalDateTime getData_analise() {
        return data_analise;
    }

    public void setData_analise(LocalDateTime data_analise) {
        this.data_analise = data_analise;
    }
}