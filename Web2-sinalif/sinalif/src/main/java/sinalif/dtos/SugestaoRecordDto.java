package sinalif.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record SugestaoRecordDto(
        Long id_sugestao,
        Long idUsuario,
        Long id_musica,
        String url_sugerida,
        String status_sugestao,
        LocalDateTime data_sugestao,
        LocalDateTime data_analise
) {}