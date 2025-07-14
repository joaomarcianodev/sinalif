package sinalif.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sinalif.dtos.SugestaoRecordDto;
import sinalif.models.Sugestao;
import sinalif.models.Usuario;
import sinalif.services.SugestaoService;
import sinalif.services.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/srv2/sugestoes")
public class SugestaoController {
    @Autowired
    private SugestaoService sugestaoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Sugestao> criarSugestao(@RequestBody @Valid SugestaoRecordDto sugestaoDto) {
        Sugestao sugestao = new Sugestao();
        BeanUtils.copyProperties(sugestaoDto, sugestao);
        Usuario usuario = usuarioService.detalharUsuario(sugestaoDto.id_usuario());
        sugestao.setUsuario(usuario);
        Sugestao novaSugestao = sugestaoService.criarSugestao(sugestao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaSugestao);
    }

    @GetMapping
    public ResponseEntity<List<SugestaoRecordDto>> listarSugestoes() {
        List<Sugestao> sugestoes = sugestaoService.listarSugestoes();
        List<SugestaoRecordDto> sugestoesDto = sugestoes.stream()
                .map(sugestao -> new SugestaoRecordDto(
                        sugestao.getId_sugestao(),
                        sugestao.getUsuario().getId_usuario(),
                        sugestao.getId_musica(),
                        sugestao.getUrl_sugerida(),
                        sugestao.getStatus_sugestao(),
                        sugestao.getData_sugestao(),
                        sugestao.getData_analise()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(sugestoesDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SugestaoRecordDto> detalharSugestao(@PathVariable Long id) {
        Sugestao sugestao = sugestaoService.detalharSugestao(id);
        SugestaoRecordDto sugestaoDto = new SugestaoRecordDto(
                sugestao.getId_sugestao(),
                sugestao.getUsuario().getId_usuario(),
                sugestao.getId_musica(),
                sugestao.getUrl_sugerida(),
                sugestao.getStatus_sugestao(),
                sugestao.getData_sugestao(),
                sugestao.getData_analise()
        );
        return ResponseEntity.ok(sugestaoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sugestao> atualizarSugestao(@PathVariable Long id, @RequestBody @Valid SugestaoRecordDto sugestaoDto) {
        Sugestao sugestaoExistente = sugestaoService.detalharSugestao(id);
        BeanUtils.copyProperties(sugestaoDto, sugestaoExistente);
        if (sugestaoDto.id_usuario() != null) {
            Usuario usuario = usuarioService.detalharUsuario(sugestaoDto.id_usuario());
            sugestaoExistente.setUsuario(usuario);
        }
        if (sugestaoDto.status_sugestao() != null && !sugestaoDto.status_sugestao().isEmpty()) {
            sugestaoExistente.setStatus_sugestao(sugestaoDto.status_sugestao());
            sugestaoExistente.setData_analise(LocalDateTime.now());
        }
        Sugestao sugestaoAtualizada = sugestaoService.atualizarSugestao(id, sugestaoExistente);
        return ResponseEntity.ok(sugestaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSugestao(@PathVariable Long id) {
        sugestaoService.deletarSugestao(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<SugestaoRecordDto>> listarSugestoesPorUsuario(@PathVariable Long idUsuario) {
        List<Sugestao> sugestoes = sugestaoService.listarSugestoesPorUsuario(idUsuario);
        List<SugestaoRecordDto> sugestoesDto = sugestoes.stream()
                .map(sugestao -> new SugestaoRecordDto(
                        sugestao.getId_sugestao(),
                        sugestao.getUsuario().getId_usuario(),
                        sugestao.getId_musica(),
                        sugestao.getUrl_sugerida(),
                        sugestao.getStatus_sugestao(),
                        sugestao.getData_sugestao(),
                        sugestao.getData_analise()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(sugestoesDto);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Sugestao> atualizarStatusSugestao(@PathVariable Long id, @RequestParam String status) {
        Sugestao sugestaoAtualizada = sugestaoService.atualizarStatusSugestao(id, status);
        return ResponseEntity.ok(sugestaoAtualizada);
    }
}