const _URL = "http://localhost:8081/api/historico";
const _URLAUX = "http://localhost:8081/api/musicas";

$( document ).ready(function() {
    listar();
    listarMusicas();
});

//! View
function editar(id, txtIdMusica){
  $("#txtID").val(id);
  $("#selectMusicas").val(txtIdMusica);

  $("#btnSalvar").hide();
  $("#btnCancelar").show();
  $("#btnEditar").show();
}

//! View
function resetar(){
  $("#txtID").val("");
  $("#selectMusicas").val("");

  $("#btnSalvar").show();
  $("#btnCancelar").hide();
  $("#btnEditar").hide();
}

//! Função GET ETIQUETAS
function listarMusicas(){
  var request = new XMLHttpRequest();

  request.open("GET", _URLAUX, true);
  request.send();

  request.onreadystatechange = function(){
    if(request.readyState == 4 && request.status == 200){
      mostrarMusicas(JSON.parse(request.responseText));
    }
  }

  //! View
  function mostrarMusicas(list){
    if(list.length>0){
      out = "<option value='' selected disabled>Selecione uma música</option>"
      var i;
      for(i=0; i<list.length; i++){
        out += `<option value="${list[i].id_musica}">${list[i].url}</option>`
      }
    }else{
      out = "<option value='' selected disabled>Não há músicas cadastradas</option>"
      $("#selectMusicas").prop("disabled", true);
    }

    document.getElementById("selectMusicas").innerHTML = out;
  }
}

//! FUNÇÃO POST
function salvar(){
  var request = new XMLHttpRequest();
  var txtIDMusica = $("#selectMusicas").val();

  request.onreadystatechange = function(){
    if(request.readyState == 4 && request.status == 200){
      $("#mensagem").text("Salvo com sucesso!");
      listar();
    }else{
      $("#mensagem").text("Referência não encontrada.");
      listar();
    }
  }

  request.open("POST", _URL, true);
  request.setRequestHeader('Content-type', 'application/json');

  var json = {
    "id_logReproducao": null,
    "id_musica": txtIDMusica,
    "data_reproducao": null
  }
  
  request.send(JSON.stringify(json));
  resetar();
}

//! Função GET
function listar(){
  var request = new XMLHttpRequest();

  request.open("GET", _URL, true);
  request.send();

  request.onreadystatechange = function(){
    if(request.readyState == 4 && request.status == 200){
      mostrar(JSON.parse(request.responseText));
    }
  }

  //! View
  function mostrar(list){
    var out = "<table border='1' class='col table table-striped'><tr><th>ID</th><th>ID Música</th><th>URL tocada</th><th>Data Reprodução</th><th>Editar</th><th>Deletar</th></tr>";

    if(list.length>0){
      var i;
      for(i=0; i<list.length; i++){
        const date = new Date(list[i].data_reproducao);

        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();

        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');

        data_reproducao_formatado = `${day}-${month}-${year} [${hours}:${minutes}:${seconds}]`;
        
        out +=
        "<tr>"+
          "<td>"+list[i].id_logReproducao+"</td>"+
          "<td>"+list[i].musica.id_musica+"</td>"+
          "<td>"+list[i].musica.url+"</td>"+
          "<td>"+data_reproducao_formatado+"</td>"+
          "<td><button class='btn btn-warning' onclick='editar("+list[i].id_logReproducao+", &apos;"+list[i].musica.id_musica+"&apos;)'>Editar</button></td>"+
          "<td><button class='btn btn-danger' onclick='deletar("+list[i].id_logReproducao+")'>Deletar</button></td>"+
        "</tr>";
      }

      out += "</table>";
    }else{
      var out = "<div class='text-center'>Não há logs registrados.</div>"
    }

    document.getElementById("listagem").innerHTML = out;
  }
}

//! Função PUT
function salvarEdicao(){
  var request = new XMLHttpRequest();
  var request_GET = new XMLHttpRequest();
  var id = $("#txtID").val();
  var txtIDMusica = $("#selectMusicas").val();

  request.onreadystatechange = function(){
    if(request.readyState == 4 && request.status == 200){
      $("#mensagem").text("Editado com sucesso!");
      listar();
    }else{
      $("#mensagem").text("Não foi possível editar.");
      listar();
    }
  }

  request_GET.onreadystatechange = function(){
    if(request_GET.readyState == 4 && request_GET.status == 200){
      receber(JSON.parse(request_GET.responseText));
    }
  }

  request_GET.open("GET", _URL+"/"+id, true);
  request_GET.send();

  function receber(log){
    request.open("PUT", _URL, true);
    request.setRequestHeader('Content-type', 'application/json');

    var json = {
      "id_logReproducao": id,
      "id_musica": txtIDMusica,
      "data_reproducao": log.data_reproducao
    }

    request.send(JSON.stringify(json));
    resetar();
  }
}

//! Função DELETE
function deletar(id){
  var request = new XMLHttpRequest();

  request.onreadystatechange = function(){
    if(request.readyState == 4 && request.status == 200){
      $("#mensagem").text("Excluido com sucesso!");
      listar();
    }else{
      $("#mensagem").text("Não foi possível excluir.");
    }
  }

  request.open("DELETE", _URL+"/"+id, true);
  request.send();
}