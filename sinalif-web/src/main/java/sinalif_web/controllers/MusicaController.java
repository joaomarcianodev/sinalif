package sinalif_web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sinalif_web.dtos.MusicaRecordDto;
import sinalif_web.models.Musica;
import sinalif_web.services.MusicaService;

@RestController
@RequestMapping("/srv1/musicas")
@CrossOrigin(origins = "*")
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

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
