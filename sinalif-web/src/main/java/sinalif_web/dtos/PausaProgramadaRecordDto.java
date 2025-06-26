package sinalif_web.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public record PausaProgramadaRecordDto(Long id_pausa,
		LocalDate data_inicio,
		LocalDate data_fim,
		LocalTime hora_inicio,
		LocalTime hora_fim,
		boolean ativo,
		LocalDate data_criacao) {}
