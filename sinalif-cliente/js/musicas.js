const _URL = "http://localhost:8081/api/musicas";

$( document ).ready(function() {
    listar();
});

//! View
function editar(id, txtUrl){
  $("#txtID").val(id);
  $("#txtUrl").val(txtUrl);

  $("#btnSalvar").hide();
  $("#btnCancelar").show();
  $("#btnEditar").show();
}

//! View
function resetar(){
  $("#txtID").val("");
  $("#txtUrl").val("");

  $("#btnSalvar").show();
  $("#btnCancelar").hide();
  $("#btnEditar").hide();
}

//! Função POST
function salvar(){
  var request = new XMLHttpRequest();
  var txtUrl = $("#txtUrl").val();

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
    "id_musica": null,
    "url": txtUrl,
    "status": "Pendente",
    "data_criacao": null
  }
  
  request.send(JSON.stringify(json));
  resetar(); //esconder botões
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
    var out = "<table border='1' class='col table table-striped'><tr><th>ID</th><th>URL</th><th>Status</th><th>Data Criação</th><th>Editar</th><th>Deletar</th></tr>";

    if(list.length>0){
      var i;
      for(i=0; i<list.length; i++){
        out +=
        "<tr>"+
          "<td>"+list[i].id_musica+"</td>"+
          "<td>"+list[i].url+"</td>"+
          "<td>"+list[i].status+"</td>"+
          "<td>"+list[i].data_criacao+"</td>"+
          "<td><button class='btn btn-warning' onclick='editar("+list[i].id_musica+", &apos;"+list[i].url+"&apos;)'>Editar</button></td>"+
          "<td><button class='btn btn-danger' onclick='deletar("+list[i].id_musica+")'>Deletar</button></td>"+
        "</tr>";
      }

      out += "</table>";
    }else{
      var out = "<div class='text-center'>Não há músicas cadastradas.</div>"
    }

    document.getElementById("listagem").innerHTML = out;
  }
}

//! Função PUT
function salvarEdicao(){
  var request = new XMLHttpRequest();
  var request_GET = new XMLHttpRequest();
  var id = $("#txtID").val();
  var txtUrl = $("#txtUrl").val();

  request.onreadystatechange = function(){
    if(request.readyState == 4 && request.status == 200){
      $("#mensagem").text("Editado com sucesso!");
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

  function receber(musica){
    request.open("PUT", _URL, true);
    request.setRequestHeader('Content-type', 'application/json');

    var json = {
      "id_musica": id,
      "url": txtUrl,
      "status": musica.status,
      "data_criacao": musica.data_criacao
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