package sinalif.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sugestao")
@EntityListeners(AuditingEntityListener.class)
public class Sugestao {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sugestao")
    private Long idSugestao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "status_sugestao")
    private String statusSugestao = "Pendente";

    @CreatedDate
    @Column(name = "data_sugestao", updatable = false)
    private LocalDateTime dataSugestao;

    @Column(name = "data_analise")
    private LocalDateTime dataAnalise = null;
}