package sinalif_srv3.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AlarmeRecordDto(
		long id_alarme,
		long id_etiqueta,
		LocalTime horario_programado,
		String dias_semana,
		boolean ativo,
		boolean pausado,
		LocalDateTime data_criacao,
		LocalDateTime data_modificacao){
}
