package sinalif.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sinalif.dtos.MusicaRecordDto;
import sinalif.models.Musica;
import sinalif.services.impl.MusicaServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/srv1/musicas")
public class MusicaController {
    @Autowired
    private MusicaServiceImpl musicaService;

    @GetMapping
    public List<Musica> listarMusicas(){
        return musicaService.listarMusicas();
    }

    @GetMapping("/{id}")
    public Musica detalharMusica(@PathVariable Long id){
        return musicaService.detalharMusica(id);
    }

    @PostMapping
    public Musica salvarMusica(@RequestBody MusicaRecordDto musicaRecordDto){
        return musicaService.salvarMusica(musicaRecordDto);
    }

    @DeleteMapping("/{id}")
    public void excluirMusica(@PathVariable Long id){
        musicaService.excluirMusica(id);
    }
}
