package sinalif_srv1.dtos;

import sinalif_srv1.models.LogReproducao;

import java.time.LocalDateTime;

public record MusicaRecordDto(
        long id_musica,
        String url,
        String status,
        LocalDateTime data_criacao){
}