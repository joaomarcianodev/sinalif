package sinalif.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AlarmeRecordDto(Long id_alarme,
		LocalTime horario_programado,
		String dias_semana,
		boolean ativo,
		boolean pausado,
		LocalDateTime data_criacao,
		LocalDateTime data_modificacao) {}
