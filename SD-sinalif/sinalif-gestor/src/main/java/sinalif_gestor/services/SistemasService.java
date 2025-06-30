package sinalif_gestor.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class SistemasService {
    private RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    private String url_srv1_musicas;
    private String url_srv1_historico;
    private String url_srv2_usuarios;
    private String url_srv2_sugestao;
    private String url_srv3_alarmes;
    private String url_srv3_etiquetas;
    private String url_srv3_pausasProgramadas;
    private String url_srv3_perfis;


    public SistemasService(){
        this.restTemplate = new RestTemplate();
        this.url_srv1_musicas = "http://localhost:8082/srv1/musicas";
        this.url_srv1_historico = "http://localhost:8082/srv1/historico";
        this.url_srv2_usuarios = "http://localhost:8083/srv2/usuarios";
        this.url_srv2_sugestao = "http://localhost:8083/srv2/sugestao";
        this.url_srv3_alarmes = "http://localhost:8084/srv3/alarmes";
        this.url_srv3_etiquetas = "http://localhost:8084/srv3/etiquetas";
        this.url_srv3_pausasProgramadas = "http://localhost:8084/srv3/pausas";
        this.url_srv3_perfis = "http://localhost:8084/srv3/perfis";
    }

    /* ********************************************************** */
    /* Métodos Servico 1 - API Músicas e Histórico de Reproduções */
    /* ********************************************************** */

    //Músicas
    public ResponseEntity<String> listarMusica(){
        return restTemplate.getForEntity(url_srv1_musicas, String.class);
    }
    public ResponseEntity<String> detalharMusica(Long id_musica){
        return restTemplate.getForEntity(url_srv1_musicas+"/"+id_musica, String.class);
    }
    public ResponseEntity<String> salvarMusica(RequestEntity<String> musica){
        return restTemplate.postForEntity(url_srv1_musicas, musica, String.class);
    }
    public ResponseEntity<String> editarMusica(RequestEntity<String> musica){
        return restTemplate.postForEntity(url_srv1_musicas, musica, String.class);
    }
    public void excluirMusica(Long id){
        restTemplate.delete(url_srv1_musicas+"/"+id);
    }

    //Histórico de Reproduções
    public ResponseEntity<String> listarLogReproducao(){
        return restTemplate.getForEntity(url_srv1_historico, String.class);
    }
    public ResponseEntity<String> detalharLogReproducao(Long id){
        return restTemplate.getForEntity(url_srv1_historico+"/"+id, String.class);
    }
    public ResponseEntity<String> salvarLogReproducao(RequestEntity<String> logRep){
        return restTemplate.postForEntity(url_srv1_historico, logRep, String.class);
    }
    public ResponseEntity<String> editarLogReproducao(RequestEntity<String> logRep){
        return restTemplate.postForEntity(url_srv1_historico, logRep, String.class);
    }
    public void excluirLogReproducao(Long id){
        restTemplate.delete(url_srv1_historico+"/"+id);
    }



    /* ******************************************** */
    /* Métodos Servico 2 - API Usuários e Sugestões */
    /* ******************************************** */

    //Usuários
    public ResponseEntity<String> listarUsuario(){
        ResponseEntity<String> responseUsuarios = restTemplate.getForEntity(url_srv2_usuarios, String.class);
        if(!responseUsuarios.getStatusCode().is2xxSuccessful()) return responseUsuarios;

        ResponseEntity<String> responseSugestao = restTemplate.getForEntity(url_srv2_sugestao, String.class);
        if(!responseSugestao.getStatusCode().is2xxSuccessful()) return responseSugestao;

        try {
            JsonNode usuarios = mapper.readTree(responseUsuarios.getBody());
            JsonNode sugestoes = mapper.readTree(responseSugestao.getBody());

            List<JsonNode> usuariosList = new ArrayList<>();
            for(JsonNode usuario : usuarios){
                String id_usuario = usuario.get("id_usuario").asText();

                List<JsonNode> sugestoesList = new ArrayList<>();
                for(JsonNode sugestao : sugestoes){
                    if(Objects.equals(sugestao.get("id_usuario").asText(), id_usuario)) sugestoesList.add(sugestao);
                }

                ((ObjectNode) usuario).set("sugestoes", mapper.valueToTree(sugestoesList));
                usuariosList.add(usuario);
            }

            return ResponseEntity.ok(mapper.writeValueAsString(usuariosList));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public ResponseEntity<String> detalharUsuario(String id_usuario){
        ResponseEntity<String> responseUsuario = restTemplate.getForEntity(url_srv2_usuarios+"/"+id_usuario, String.class);
        if(!responseUsuario.getStatusCode().is2xxSuccessful()) return responseUsuario;

        ResponseEntity<String> responseSugestao = restTemplate.getForEntity(url_srv2_sugestao, String.class);
        if(!responseSugestao.getStatusCode().is2xxSuccessful()) return responseSugestao;

        try {
            JsonNode usuario = mapper.readTree(responseUsuario.getBody());
            JsonNode sugestoes = mapper.readTree(responseSugestao.getBody());

            List<JsonNode> sugestoesList = new ArrayList<>();
            for(JsonNode sugestao : sugestoes){
                if(Objects.equals(sugestao.get("id_usuario").asText(), id_usuario)) sugestoesList.add(sugestao);
            }

            ((ObjectNode) usuario).set("sugestoes", mapper.valueToTree(sugestoesList));
            return ResponseEntity.ok(mapper.writeValueAsString(usuario));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public ResponseEntity<String> salvarUsuario(RequestEntity<String> usuario){
        return restTemplate.postForEntity(url_srv2_usuarios, usuario, String.class);
    }
    public ResponseEntity<String> editarUsuario(RequestEntity<String> usuario, String id){
        return restTemplate.exchange(url_srv2_usuarios+"/"+id, HttpMethod.PUT, usuario, String.class);
    }
    public void excluirUsuario(String id_usuario){
        ResponseEntity<String> responseSugestao = restTemplate.getForEntity(url_srv2_sugestao, String.class);
        if(responseSugestao.getStatusCode().is2xxSuccessful()){
            try {
                JsonNode sugestoes = mapper.readTree(responseSugestao.getBody());
                for(JsonNode sugestao : sugestoes){
                    if(Objects.equals(id_usuario, sugestao.get("id_usuario").asText())){
                        restTemplate.delete(url_srv2_sugestao+"/"+sugestao.get("id_sugestao").asText());
                    }
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        restTemplate.delete(url_srv2_usuarios+"/"+id_usuario);
    }

    //Sugestões
    public ResponseEntity<String> listarSugestao(){
        return restTemplate.getForEntity(url_srv2_sugestao, String.class);
    }
    public ResponseEntity<String> detalharSugestao(String id){
        return restTemplate.getForEntity(url_srv2_sugestao+"/"+id, String.class);
    }
    public ResponseEntity<String> salvarSugestao(RequestEntity<String> sugestao){
        try {
            JsonNode sugestaoJson = mapper.readTree(sugestao.getBody());
            String urlSugerida = sugestaoJson.get("url_sugerida").asText();

            ObjectNode musica = mapper.createObjectNode();
            musica.put("url", urlSugerida);
            musica.putNull("status");
            musica.put("data_sugestao", LocalDateTime.now().toString());
            String musicaJsonRequest = mapper.writeValueAsString(musica);
            RequestEntity<String> requestMusica = RequestEntity
                    .post(URI.create(url_srv1_musicas))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(musicaJsonRequest);

            ResponseEntity<String> responseMusica = restTemplate.exchange(requestMusica, String.class);
            if (responseMusica.getStatusCode().is2xxSuccessful()) {
                JsonNode musicaJson = mapper.readTree(responseMusica.getBody());

                ((ObjectNode) sugestaoJson).set("id_musica", musicaJson.get("id_musica"));
                String sugestaoJsonRequest = mapper.writeValueAsString(sugestaoJson);
                RequestEntity<String> responseSugestao = RequestEntity
                        .post(URI.create(url_srv2_sugestao))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(sugestaoJsonRequest);

                return restTemplate.exchange(responseSugestao, String.class);
            } else {
                return responseMusica;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public ResponseEntity<String> editarSugestao(RequestEntity<String> sugestao, String id){
        return restTemplate.exchange(url_srv2_sugestao+"/"+id, HttpMethod.PUT, sugestao, String.class);
    }
    public void excluirSugestao(String id){
        restTemplate.delete(url_srv2_sugestao+"/"+id);
    }

    /* ****************************************** */
    /* Métodos Serviço 3 - API Sinalif_srv3 (Alarmes, Etiquetas, Pausas, Perfis) */
    /* ****************************************** */

    // Alarmes
    public ResponseEntity<String> listarAlarmes(){
        return restTemplate.getForEntity(url_srv3_alarmes, String.class);
    }

    public ResponseEntity<String> detalharAlarme(Long id){
        return restTemplate.getForEntity(url_srv3_alarmes + "/" + id, String.class);
    }

    public ResponseEntity<String> salvarAlarme(RequestEntity<String> alarme){
        return restTemplate.postForEntity(url_srv3_alarmes, alarme, String.class);
    }

    public ResponseEntity<String> atualizarAlarme(Long id, String alarmeJson){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<String> requestEntity = new RequestEntity<>(alarmeJson, headers, HttpMethod.PUT, null);
        return restTemplate.exchange(url_srv3_alarmes + "/" + id, HttpMethod.PUT, requestEntity, String.class);
    }

    public void excluirAlarme(Long id){
        restTemplate.delete(url_srv3_alarmes + "/" + id);
    }

    // Etiquetas
    public ResponseEntity<String> listarEtiquetas(){
        return restTemplate.getForEntity(url_srv3_etiquetas, String.class);
    }

    public ResponseEntity<String> detalharEtiqueta(Long id){
        return restTemplate.getForEntity(url_srv3_etiquetas + "/" + id, String.class);
    }

    public ResponseEntity<String> salvarEtiqueta(RequestEntity<String> etiqueta){
        return restTemplate.postForEntity(url_srv3_etiquetas, etiqueta, String.class);
    }

    public ResponseEntity<String> atualizarEtiqueta(Long id, String etiquetaJson){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<String> requestEntity = new RequestEntity<>(etiquetaJson, headers, HttpMethod.PUT, null);
        return restTemplate.exchange(url_srv3_etiquetas + "/" + id, HttpMethod.PUT, requestEntity, String.class);
    }

    public void excluirEtiqueta(Long id){
        restTemplate.delete(url_srv3_etiquetas + "/" + id);
    }

    // Pausas Programadas
    public ResponseEntity<String> listarPausasProgramadas(){
        return restTemplate.getForEntity(url_srv3_pausasProgramadas, String.class);
    }

    public ResponseEntity<String> detalharPausaProgramada(Long id){
        return restTemplate.getForEntity(url_srv3_pausasProgramadas + "/" + id, String.class);
    }

    public ResponseEntity<String> salvarPausaProgramada(RequestEntity<String> pausa){
        return restTemplate.postForEntity(url_srv3_pausasProgramadas, pausa, String.class);
    }

    public ResponseEntity<String> atualizarPausaProgramada(Long id, String pausaJson){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<String> requestEntity = new RequestEntity<>(pausaJson, headers, HttpMethod.PUT, null);
        return restTemplate.exchange(url_srv3_pausasProgramadas + "/" + id, HttpMethod.PUT, requestEntity, String.class);
    }

    public void excluirPausaProgramada(Long id){
        restTemplate.delete(url_srv3_pausasProgramadas + "/" + id);
    }

    // Perfis
    public ResponseEntity<String> listarPerfis(){
        return restTemplate.getForEntity(url_srv3_perfis, String.class);
    }

    public ResponseEntity<String> detalharPerfil(Long id){
        return restTemplate.getForEntity(url_srv3_perfis + "/" + id, String.class);
    }

    public ResponseEntity<String> salvarPerfil(RequestEntity<String> perfil){
        return restTemplate.postForEntity(url_srv3_perfis, perfil, String.class);
    }

    public ResponseEntity<String> atualizarPerfil(Long id, String perfilJson){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<String> requestEntity = new RequestEntity<>(perfilJson, headers, HttpMethod.PUT, null);
        return restTemplate.exchange(url_srv3_perfis + "/" + id, HttpMethod.PUT, requestEntity, String.class);
    }

    public void excluirPerfil(Long id){
        restTemplate.delete(url_srv3_perfis + "/" + id);
    }
}