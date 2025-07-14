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

    @Column(name = "id_musica", nullable = false)
    private Long id_musica;

    @Column(name = "url_sugerida", nullable = false)
    private String url_sugerida;

    @Column(name = "status_sugestao", nullable = false)
    private String status_sugestao = "Pendente";

    @CreatedDate
    @Column(name = "data_sugestao", updatable = false)
    private LocalDateTime data_sugestao;

    @Column(name = "data_analise")
    private LocalDateTime data_analise = null;
}