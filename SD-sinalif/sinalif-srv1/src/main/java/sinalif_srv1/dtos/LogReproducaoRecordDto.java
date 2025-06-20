package sinalif_srv1.dtos;

import java.time.LocalDateTime;

public record LogReproducaoRecordDto(
    long id_logReproducao,
    long id_musica,
    LocalDateTime data_reproducao){
}