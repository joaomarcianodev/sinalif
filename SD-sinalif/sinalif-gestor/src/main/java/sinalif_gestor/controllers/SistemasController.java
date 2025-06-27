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

    @PutMapping("/sugestao/{id}")
    public ResponseEntity<String> editarSugestao(RequestEntity<String> sugestao, @PathVariable String id){
        return sistemasService.editarSugestao(sugestao, id); }

    @DeleteMapping("/sugestao/{id}")
    public void excluirSugestao(@PathVariable String id){
        sistemasService.excluirSugestao(id);
    }

    /* ********************************************************** */
    /* Métodos Serviço 3 - API Alarmes, Etiquetas, Pausas, Perfis */
    /* ********************************************************** */

    //Alarmes
    @GetMapping("/alarmes")
    public ResponseEntity<String> listarAlarmes(){
        return sistemasService.listarAlarmes();
    }

    @GetMapping("/alarmes/{id}")
    public ResponseEntity<String> detalharAlarme(@PathVariable Long id){
        return sistemasService.detalharAlarme(id);
    }

    @PostMapping("/alarmes")
    public ResponseEntity<String> salvarAlarme(RequestEntity<String> alarme){
        return sistemasService.salvarAlarme(alarme);
    }

    @PutMapping("/alarmes/{id}")
    public ResponseEntity<String> atualizarAlarme(@PathVariable Long id, @RequestBody String alarmeJson){
        return sistemasService.atualizarAlarme(id, alarmeJson);
    }

    @DeleteMapping("/alarmes/{id}")
    public void excluirAlarme(@PathVariable Long id){
        sistemasService.excluirAlarme(id);
    }

    // Etiquetas
    @GetMapping("/etiquetas")
    public ResponseEntity<String> listarEtiquetas(){
        return sistemasService.listarEtiquetas();
    }

    @GetMapping("/etiquetas/{id}")
    public ResponseEntity<String> detalharEtiqueta(@PathVariable Long id){
        return sistemasService.detalharEtiqueta(id);
    }

    @PostMapping("/etiquetas")
    public ResponseEntity<String> salvarEtiqueta(RequestEntity<String> etiqueta){
        return sistemasService.salvarEtiqueta(etiqueta);
    }

    @PutMapping("/etiquetas/{id}")
    public ResponseEntity<String> atualizarEtiqueta(@PathVariable Long id, @RequestBody String etiquetaJson){
        return sistemasService.atualizarEtiqueta(id, etiquetaJson);
    }

    @DeleteMapping("/etiquetas/{id}")
    public void excluirEtiqueta(@PathVariable Long id){
        sistemasService.excluirEtiqueta(id);
    }

    // Pausas Programadas
    @GetMapping("/pausas")
    public ResponseEntity<String> listarPausasProgramadas(){
        return sistemasService.listarPausasProgramadas();
    }

    @GetMapping("/pausas/{id}")
    public ResponseEntity<String> detalharPausaProgramada(@PathVariable Long id){
        return sistemasService.detalharPausaProgramada(id);
    }

    @PostMapping("/pausas")
    public ResponseEntity<String> salvarPausaProgramada(RequestEntity<String> pausa){
        return sistemasService.salvarPausaProgramada(pausa);
    }

    @PutMapping("/pausas/{id}")
    public ResponseEntity<String> atualizarPausaProgramada(@PathVariable Long id, @RequestBody String pausaJson){
        return sistemasService.atualizarPausaProgramada(id, pausaJson);
    }

    @DeleteMapping("/pausas/{id}")
    public void excluirPausaProgramada(@PathVariable Long id){
        sistemasService.excluirPausaProgramada(id);
    }

    // Perfis
    @GetMapping("/perfis")
    public ResponseEntity<String> listarPerfis(){
        return sistemasService.listarPerfis();
    }

    @GetMapping("/perfis/{id}")
    public ResponseEntity<String> detalharPerfil(@PathVariable Long id){
        return sistemasService.detalharPerfil(id);
    }

    @PostMapping("/perfis")
    public ResponseEntity<String> salvarPerfil(RequestEntity<String> perfil){
        return sistemasService.salvarPerfil(perfil);
    }

    @PutMapping("/perfis/{id}")
    public ResponseEntity<String> atualizarPerfil(@PathVariable Long id, @RequestBody String perfilJson){
        return sistemasService.atualizarPerfil(id, perfilJson);
    }

    @DeleteMapping("/perfis/{id}")
    public void excluirPerfil(@PathVariable Long id){
        sistemasService.excluirPerfil(id);
    }
}