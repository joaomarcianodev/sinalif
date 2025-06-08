package sinalif_gestor.services;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SistemasService {
    private RestTemplate restTemplate;

    private String url_srv1_musicas;
    private String url_srv1_historico;
    private String url_srv2_usuarios;
    private String url_srv2_sugestao;

    public SistemasService(){
        this.restTemplate = new RestTemplate();
        this.url_srv1_musicas = "http://localhost:8082/srv1/musicas";
        this.url_srv1_historico = "http://localhost:8082/srv1/historico";
        this.url_srv2_usuarios = "http://localhost:8083/srv2/usuarios";
        this.url_srv2_sugestao = "http://localhost:8083/srv2/sugestao";
    }

    /* ********************************************************** */
    /* Métodos Servico 1 - API Músicas e Histórico de Reproduções */
    /* ********************************************************** */

    //Músicas
    public ResponseEntity<String> listarMusica(){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv1_musicas, String.class);
        return response;
    }
    public ResponseEntity<String> detalharMusica(Long id){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv1_musicas+"/"+id, String.class);
        return response;
    }
    public ResponseEntity<String> salvarMusica(RequestEntity<String> musica){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv1_musicas, musica, String.class);
        return response;
    }
    public ResponseEntity<String> editarMusica(RequestEntity<String> musica, Long id){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv1_musicas+"/"+id, musica, String.class);
        return response;
    }
    public void excluirMusica(Long id){
        restTemplate.delete(url_srv1_musicas+"/"+id);
    }

    //Histórico de Reproduções
    public ResponseEntity<String> listarLogReproducao(){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv1_historico, String.class);
        return response;
    }
    public ResponseEntity<String> detalharLogReproducao(Long id){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv1_historico+"/"+id, String.class);
        return response;
    }
    public ResponseEntity<String> salvarLogReproducao(RequestEntity<String> logRep){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv1_historico, logRep, String.class);
        return response;
    }
    public ResponseEntity<String> editarLogReproducao(RequestEntity<String> logRep, Long id){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv1_historico+"/"+id, logRep, String.class);
        return response;
    }
    public void excluirLogReproducao(Long id){
        restTemplate.delete(url_srv1_historico+"/"+id);
    }



    /* ******************************************** */
    /* Métodos Servico 2 - API Usuários e Sugestões */
    /* ******************************************** */

    //Usuários
    public ResponseEntity<String> listarUsuario(){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv2_usuarios, String.class);
        return response;
    }
    public ResponseEntity<String> detalharUsuario(String id){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv2_usuarios+"/"+id, String.class);
        return response;
    }
    public ResponseEntity<String> salvarUsuario(RequestEntity<String> usuario){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv2_usuarios, usuario, String.class);
        return response;
    }
    public ResponseEntity<String> editarUsuario(RequestEntity<String> usuario, String id){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv2_usuarios+"/"+id, usuario, String.class);
        return response;
    }
    public void excluirUsuario(String id){
        restTemplate.delete(url_srv2_usuarios+"/"+id);
    }

    //Sugestões
    public ResponseEntity<String> listarSugestao(){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv2_sugestao, String.class);
        return response;
    }
    public ResponseEntity<String> detalharSugestao(String id){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv2_sugestao+"/"+id, String.class);
        return response;
    }
    public ResponseEntity<String> salvarSugestao(RequestEntity<String> sugestao){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv2_sugestao, sugestao, String.class);
        return response;
    }
    public ResponseEntity<String> editarSugestao(RequestEntity<String> sugestao, String id){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv2_sugestao+"/"+id, sugestao, String.class);
        return response;
    }
    public void excluirSugestao(String id){
        restTemplate.delete(url_srv2_sugestao+"/"+id);
    }
}
