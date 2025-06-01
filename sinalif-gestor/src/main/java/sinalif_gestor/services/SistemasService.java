package sinalif_gestor.services;

import org.springframework.http.HttpMethod;
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
    public ResponseEntity<String> salvarMusica(RequestEntity<String> musica){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv1_musicas, musica, String.class);
        return response;
    }
    public void excluirMusica(RequestEntity<String> musica){
        restTemplate.exchange(url_srv1_musicas, HttpMethod.DELETE, musica, String.class);
    }

    //Histórico de Reproduções
    public ResponseEntity<String> listarLogReproducao(){
        ResponseEntity<String> response = restTemplate.getForEntity(url_srv1_historico, String.class);
        return response;
    }
    public ResponseEntity<String> salvarLogReproducao(RequestEntity<String> logRep){
        ResponseEntity<String> response = restTemplate.postForEntity(url_srv1_historico, logRep, String.class);
        return response;
    }
    public void excluirLogReproducao(RequestEntity<String> logRep){
        restTemplate.exchange(url_srv1_historico, HttpMethod.DELETE, logRep, String.class);
    }
}
