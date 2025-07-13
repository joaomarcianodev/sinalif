package sinalif.dtos;

import sinalif.models.Perfil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record UsuarioRecordDto(
    Long id,
    String nome,
    String email,
    LocalDateTime data_criacao,
    List<Perfil> roles,
    String novaSenha,
    String confirmarSenha
) {}