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

    /* ********************************************************** */
    /* Métodos Serviço 3 - API Alarmes, Etiquetas, Pausas, Perfis */
    /* ********************************************************** */

    //Alarmes
    @GetMapping("/alarmes")
    public ResponseEntity<String> listarAlarmesSrv3(){
        return sistemasService.listarAlarmesSrv3();
    }

    @GetMapping("/alarmes/{id}")
    public ResponseEntity<String> detalharAlarmeSrv3(@PathVariable Long id){
        return sistemasService.detalharAlarmeSrv3(id);
    }

    @PostMapping("/alarmes")
    public ResponseEntity<String> salvarAlarmeSrv3(RequestEntity<String> alarme){
        return sistemasService.salvarAlarmeSrv3(alarme);
    }

    @PutMapping("/alarmes/{id}")
    public ResponseEntity<String> atualizarAlarmeSrv3(@PathVariable Long id, @RequestBody String alarmeJson){
        return sistemasService.atualizarAlarmeSrv3(id, alarmeJson);
    }

    @DeleteMapping("/alarmes/{id}")
    public void excluirAlarmeSrv3(@PathVariable Long id){
        sistemasService.excluirAlarmeSrv3(id);
    }

    // Etiquetas
    @GetMapping("/etiquetas")
    public ResponseEntity<String> listarEtiquetasSrv3(){
        return sistemasService.listarEtiquetasSrv3();
    }

    @GetMapping("/etiquetas/{id}")
    public ResponseEntity<String> detalharEtiquetaSrv3(@PathVariable Long id){
        return sistemasService.detalharEtiquetaSrv3(id);
    }

    @PostMapping("/etiquetas")
    public ResponseEntity<String> salvarEtiquetaSrv3(RequestEntity<String> etiqueta){
        return sistemasService.salvarEtiquetaSrv3(etiqueta);
    }

    @PutMapping("/etiquetas/{id}")
    public ResponseEntity<String> atualizarEtiquetaSrv3(@PathVariable Long id, @RequestBody String etiquetaJson){
        return sistemasService.atualizarEtiquetaSrv3(id, etiquetaJson);
    }

    @DeleteMapping("/etiquetas/{id}")
    public void excluirEtiquetaSrv3(@PathVariable Long id){
        sistemasService.excluirEtiquetaSrv3(id);
    }

    // Pausas Programadas
    @GetMapping("/pausasProgramadas")
    public ResponseEntity<String> listarPausasProgramadasSrv3(){
        return sistemasService.listarPausasProgramadasSrv3();
    }

    @GetMapping("/pausasProgramadas/{id}")
    public ResponseEntity<String> detalharPausaProgramadaSrv3(@PathVariable Long id){
        return sistemasService.detalharPausaProgramadaSrv3(id);
    }

    @PostMapping("/pausasProgramadas")
    public ResponseEntity<String> salvarPausaProgramadaSrv3(RequestEntity<String> pausa){
        return sistemasService.salvarPausaProgramadaSrv3(pausa);
    }

    @PutMapping("/pausasProgramadas/{id}")
    public ResponseEntity<String> atualizarPausaProgramadaSrv3(@PathVariable Long id, @RequestBody String pausaJson){
        return sistemasService.atualizarPausaProgramadaSrv3(id, pausaJson);
    }

    @DeleteMapping("/pausasProgramadas/{id}")
    public void excluirPausaProgramadaSrv3(@PathVariable Long id){
        sistemasService.excluirPausaProgramadaSrv3(id);
    }

    // Perfis
    @GetMapping("/perfis")
    public ResponseEntity<String> listarPerfisSrv3(){
        return sistemasService.listarPerfisSrv3();
    }

    @GetMapping("/perfis/{id}")
    public ResponseEntity<String> detalharPerfilSrv3(@PathVariable Long id){
        return sistemasService.detalharPerfilSrv3(id);
    }

    @PostMapping("/perfis")
    public ResponseEntity<String> salvarPerfilSrv3(RequestEntity<String> perfil){
        return sistemasService.salvarPerfilSrv3(perfil);
    }

    @PutMapping("/perfis/{id}")
    public ResponseEntity<String> atualizarPerfilSrv3(@PathVariable Long id, @RequestBody String perfilJson){
        return sistemasService.atualizarPerfilSrv3(id, perfilJson);
    }

    @DeleteMapping("/perfis/{id}")
    public void excluirPerfilSrv3(@PathVariable Long id){
        sistemasService.excluirPerfilSrv3(id);
    }
}