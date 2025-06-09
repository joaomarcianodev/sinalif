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
    public ResponseEntity<String> listarMusica(){
        return sistemasService.listarMusica();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/musicas/{id}")
    public ResponseEntity<String> detalharMusica(@PathVariable Long id){
        return sistemasService.detalharMusica(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/musicas")
    public ResponseEntity<String> salvarMusica(RequestEntity<String> musica){ return sistemasService.salvarMusica(musica); }

    @CrossOrigin(origins = "*")
    @PutMapping("/musicas/{id}")
    public ResponseEntity<String> editarMusica(RequestEntity<String> musica, @PathVariable Long id){ return sistemasService.editarMusica(musica, id); }

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
    @PutMapping("/historico/{id}")
    public ResponseEntity<String> editarLogReproducao(RequestEntity<String> logRep, @PathVariable Long id){
        return sistemasService.editarLogReproducao(logRep, id);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/historico/{id}")
    public void excluirLogReproducao(@PathVariable Long id){
        sistemasService.excluirLogReproducao(id);
    }



    /* ******************************************** */
    /* Métodos Servico 2 - API Usuários e Sugestões */
    /* ******************************************** */

    //Usuários
    @CrossOrigin(origins = "*")
    @GetMapping("/usuarios")
    public ResponseEntity<String> listarUsuario(){
        return sistemasService.listarUsuario();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<String> detalharUsuario(@PathVariable String id){
        return sistemasService.detalharUsuario(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/usuarios")
    public ResponseEntity<String> salvarUsuario(RequestEntity<String> usuario){ return sistemasService.salvarUsuario(usuario); }

    @CrossOrigin(origins = "*")
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<String> editarUsuario(RequestEntity<String> usuario, @PathVariable String id){ return sistemasService.editarUsuario(usuario, id); }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/usuarios/{id}")
    public void excluirUsuario(@PathVariable String id){
        sistemasService.excluirUsuario(id);
    }

    //Sugestões
    @CrossOrigin(origins = "*")
    @GetMapping("/sugestao")
    public ResponseEntity<String> listarSugestao(){
        return sistemasService.listarSugestao();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/sugestao/{id}")
    public ResponseEntity<String> detalharSugestao(@PathVariable String id){
        return sistemasService.detalharSugestao(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/sugestao")
    public ResponseEntity<String> salvarSugestao(RequestEntity<String> sugestao){
        return sistemasService.salvarSugestao(sugestao); }

    @CrossOrigin(origins = "*")
    @PutMapping("/sugestao/{id}")
    public ResponseEntity<String> editarSugestao(RequestEntity<String> sugestao, @PathVariable String id){
        return sistemasService.editarSugestao(sugestao, id); }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/sugestao/{id}")
    public void excluirSugestao(@PathVariable String id){
        sistemasService.excluirSugestao(id);
    }
}
