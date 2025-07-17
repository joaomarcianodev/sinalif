package sinalif.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "musica")
@EntityListeners(AuditingEntityListener.class)
public class Musica {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_musica")
    private Long idMusica;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotBlank(message= "URL é um campo obrigatório")
    @Column(name = "url", nullable = false)
    private String url;

    @CreatedDate
    @Column(name = "dataCriacao", updatable = false)
    private LocalDateTime dataCriacao;

//    @OneToMany(mappedBy = "musica", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private Set<LogReproducao> logs;

    @OneToMany(mappedBy = "musica", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<LogReproducao> logs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Musica musica = (Musica) o;
        // Compara apenas pelo ID, e só se ele não for nulo
        return idMusica != null && Objects.equals(idMusica, musica.idMusica);
    }

    @Override
    public int hashCode() {
        // Usa uma constante para objetos ainda não persistidos
        return getClass().hashCode();
    }
}
