package prova.sinalif.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "musica")
@EntityListeners(AuditingEntityListener.class)
public class Musica {

    //private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id_musica;

    @NotNull(message= "Este campo é obrigatório")
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "aceita", nullable = true)
    private boolean aceita = false;

    @CreatedDate
    @Column(name = "data_criacao", nullable = true)
    private LocalDateTime data_criacao;

    public String getTime(){
        String dataDeCriacao = "";

        return dataDeCriacao;
    }

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

    public boolean isAceita() {
        return aceita;
    }

    public void setAceita(boolean aceita) {
        this.aceita = aceita;
    }

    public LocalDateTime getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDateTime data_criacao) {
        this.data_criacao = data_criacao;
    }
}
