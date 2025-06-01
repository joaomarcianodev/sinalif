package prova.sinalif.dtos;

import prova.sinalif.models.Musica;

import java.time.LocalDateTime;

public record LogReproducaoRecordDto(
    long id_logReproducao,
    long id_musica,
    LocalDateTime data_reproducao){
}
