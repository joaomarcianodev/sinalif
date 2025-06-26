package sinalif_web.dtos;


import java.time.LocalDate;

import sinalif_web.models.Perfil;

public record UsuarioRecordDto(
    Integer id,
    String nome,
    String email,
    LocalDate data_criacao,
    Perfil perfil
) {}