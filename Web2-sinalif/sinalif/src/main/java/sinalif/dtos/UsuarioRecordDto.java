package sinalif.dtos;

import sinalif.models.Perfil;

import java.time.LocalDate;

public record UsuarioRecordDto(
    Integer id,
    String nome,
    String email,
    LocalDate data_criacao,
    Perfil perfil
) {}