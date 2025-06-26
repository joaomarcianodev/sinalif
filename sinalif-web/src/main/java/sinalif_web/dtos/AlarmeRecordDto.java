package sinalif_web.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public record AlarmeRecordDto(Long id_alarme,
		LocalTime horario_programado,
		String dias_semana,
		boolean ativo,
		boolean pausado,
		LocalDate data_criacao,
		LocalDate data_modificacao) {}
