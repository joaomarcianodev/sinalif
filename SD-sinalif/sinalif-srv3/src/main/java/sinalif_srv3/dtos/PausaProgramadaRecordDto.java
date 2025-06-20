package sinalif_srv3.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record PausaProgramadaRecordDto(
		long id_pausa,
		LocalDate data_inicio,
		LocalDate data_fim,
		LocalTime hora_inicio,
		LocalTime hora_fim,
		boolean ativo,
		LocalDateTime data_criacao){
}
