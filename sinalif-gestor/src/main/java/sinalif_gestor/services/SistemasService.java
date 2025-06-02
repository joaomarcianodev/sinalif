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
    private String url_srv2;

    public SistemasService(){
        this.restTemplate = new RestTemplate();
        this.url_srv1_musicas = "http://localhost:8081/srv1/musicas";
        this.url_srv1_historico = "http://localhost:8081/srv1/historico";
        this.url_srv2 = "";
    }

    /* ********************************************************** */
    /* Métodos Servico 1 - API Músicas e Histórico de Reproduções */
    /* ********************************************************** */

    //Músicas
    public ResponseEntity<String> listarMusicas(){
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
    public void excluirLogReproducao(Long id){
        restTemplate.delete(url_srv1_historico+"/"+id);
    }
}
