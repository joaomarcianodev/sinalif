package sinalif_gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sinalif_gestor.services.SistemasService;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
public class SistemasController {

    @Autowired
    private SistemasService sistemasService;

    /* ********************************************************** */
    /* Métodos Servico 1 - API Músicas e Histórico de Reproduções */
    /* ********************************************************** */

    //Músicas

    @GetMapping("/musicas")
    public ResponseEntity<String> listarMusica(){
        return sistemasService.listarMusica();
    }

    @GetMapping("/musicas/{id}")
    public ResponseEntity<String> detalharMusica(@PathVariable Long id){
        return sistemasService.detalharMusica(id);
    }

    @PostMapping("/musicas")
    public ResponseEntity<String> salvarMusica(RequestEntity<String> musica){ return sistemasService.salvarMusica(musica); }

    @PutMapping("/musicas")
    public ResponseEntity<String> editarMusica(RequestEntity<String> musica){ return sistemasService.editarMusica(musica); }

    @DeleteMapping("/musicas/{id}")
    public void excluirMusica(@PathVariable Long id){
        sistemasService.excluirMusica(id);
    }

    //Histórico
    @GetMapping("/historico")
    public ResponseEntity<String> listarLogReproducao(){
        return sistemasService.listarLogReproducao();
    }

    @GetMapping("/historico/{id}")
    public ResponseEntity<String> detalharLogReproducao(@PathVariable Long id){
        return sistemasService.detalharLogReproducao(id);
    }

    @PostMapping("/historico")
    public ResponseEntity<String> salvarLogReproducao(RequestEntity<String> logRep){
        return sistemasService.salvarLogReproducao(logRep);
    }

    @PutMapping("/historico")
    public ResponseEntity<String> editarLogReproducao(RequestEntity<String> logRep){
        return sistemasService.editarLogReproducao(logRep);
    }

    @DeleteMapping("/historico/{id}")
    public void excluirLogReproducao(@PathVariable Long id){
        sistemasService.excluirLogReproducao(id);
    }



    /* ******************************************** */
    /* Métodos Servico 2 - API Usuários e Sugestões */
    /* ******************************************** */

    //Usuários
    @GetMapping("/usuarios")
    public ResponseEntity<String> listarUsuario(){
        return sistemasService.listarUsuario();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<String> detalharUsuario(@PathVariable String id){
        return sistemasService.detalharUsuario(id);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<String> salvarUsuario(RequestEntity<String> usuario){ return sistemasService.salvarUsuario(usuario); }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<String> editarUsuario(RequestEntity<String> usuario, @PathVariable String id){ return sistemasService.editarUsuario(usuario, id); }

    @DeleteMapping("/usuarios/{id}")
    public void excluirUsuario(@PathVariable String id){
        sistemasService.excluirUsuario(id);
    }

    //Sugestões
    @GetMapping("/sugestao")
    public ResponseEntity<String> listarSugestao(){
        return sistemasService.listarSugestao();
    }

    @GetMapping("/sugestao/{id}")
    public ResponseEntity<String> detalharSugestao(@PathVariable String id){
        return sistemasService.detalharSugestao(id);
    }

    @PostMapping("/sugestao")
    public ResponseEntity<String> salvarSugestao(RequestEntity<String> sugestao){
        return sistemasService.salvarSugestao(sugestao); }

    @PutMapping("/sugestao")
    public ResponseEntity<String> editarSugestao(RequestEntity<String> sugestao){
        return sistemasService.editarSugestao(sugestao); }

    @DeleteMapping("/sugestao/{id}")
    public void excluirSugestao(@PathVariable String id){
        sistemasService.excluirSugestao(id);
    }
}
