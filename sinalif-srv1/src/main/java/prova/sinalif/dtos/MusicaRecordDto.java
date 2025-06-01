package prova.sinalif.dtos;

import java.time.LocalDateTime;

public record MusicaRecordDto(
        long id_musica,
        String url,
        boolean aceita,
        LocalDateTime data_criacao){
}