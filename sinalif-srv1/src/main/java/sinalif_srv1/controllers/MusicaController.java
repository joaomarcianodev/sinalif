package sinalif_srv1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sinalif_srv1.dtos.MusicaRecordDto;
import sinalif_srv1.models.Musica;
import sinalif_srv1.services.MusicaService;

import java.util.List;

@RestController
@RequestMapping("/srv1/musicas")
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

    @CrossOrigin(origins = "*") //de qualquer origem
    @GetMapping
    public List<Musica> listarMusicas(){
        return musicaService.listarMusicas();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public Musica detalharMusica(@PathVariable Long id){
        return musicaService.detalharMusica(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public Musica salvarMusica(@RequestBody MusicaRecordDto musicaRecordDto){
        return musicaService.salvarMusica(musicaRecordDto);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public void excluirMusica(@PathVariable Long id){
        musicaService.excluirMusica(id);
    }
}
