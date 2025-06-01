package sinalif_gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sinalif_gestor.services.SistemasService;

@RestController
@RequestMapping("/api/sistemas")
public class SistemasController {

    @Autowired
    private SistemasService sistemasService;

    /* ********************************************************** */
    /* Métodos Servico 1 - API Músicas e Histórico de Reproduções */
    /* ********************************************************** */

    //Músicas
    @CrossOrigin(origins = "*")
    @GetMapping("/musicas")
    public ResponseEntity<String> listarMusicas(){
        return sistemasService.listarMusicas();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/musicas/{id}")
    public ResponseEntity<String> detalharMusica(@PathVariable Long id){
        return sistemasService.detalharMusica(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/musicas")
    public ResponseEntity<String> salvarMusica(RequestEntity<String> musica){
        return sistemasService.salvarMusica(musica);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/musicas/{id}")
    public void excluirMusica(@PathVariable Long id){
        sistemasService.excluirMusica(id);
    }

    //Histórico
    @CrossOrigin(origins = "*")
    @GetMapping("/historico")
    public ResponseEntity<String> listarLogReproducao(){
        return sistemasService.listarLogReproducao();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/historico/{id}")
    public ResponseEntity<String> detalharLogReproducao(@PathVariable Long id){
        return sistemasService.detalharLogReproducao(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/historico")
    public ResponseEntity<String> salvarLogReproducao(RequestEntity<String> logRep){
        return sistemasService.salvarLogReproducao(logRep);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/historico/{id}")
    public void excluirLogReproducao(@PathVariable Long id){
        sistemasService.excluirLogReproducao(id);
    }
}
