$( document ).ready(function() {
    listar();
    // $("#btnEditar").hide();
    // $("#btnSalvar").show();
});

const _URL = "http://localhost:8081/api/musicas";

function salvar(){
  var request = new XMLHttpRequest();
  var txtUrl = $("#txtUrl").val();

  request.onreadystatechange = function(){
    if(request.readyState == 4 && request.status == 200){
      $("#mensagem").text("Salvo com sucesso!");
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
}

function listar(){
  var request = new XMLHttpRequest();

  request.open("GET", _URL, true);
  request.send();

  request.onreadystatechange = function(){
    if(request.readyState == 4 && request.status == 200){
      mostrarMusicas(JSON.parse(request.responseText));
    }
  }

  function mostrarMusicas(listMusicas){
    var out = "<table border='1' class='col table table-striped'><tr><th>ID</th><th>URL</th><th>Status</th><th>Data de Criação</th><th>Editar</th><th>Deletar</th></tr>";

    var i;
    for(i=0; i<listMusicas.length; i++){
      out +=
      "<tr>"+
        "<td>"+listMusicas[i].id_musica+"</td>"+
        "<td>"+listMusicas[i].url+"</td>"+
        "<td>"+listMusicas[i].status+"</td>"+
        "<td>"+listMusicas[i].data_criacao+"</td>"+
        "<td><button class='btn btn-warning' onclick='editar("+listMusicas[i].id_musica+", &apos;"+listMusicas[i].url+"&apos;)'>Editar</button></td>"+
        "<td><button class='btn btn-danger' onclick='deletar("+listMusicas[i].id_musica+")'>Deletar</button></td>"+
      "</tr>";
    }

    out += "</table>";

    document.getElementById("conteudo").innerHTML = out;
  }
}

function editar(id, txtUrl){
  $("#txtUrl").val(txtUrl);
  $("#txtID").val(id);

  $("#btnSalvar").hide();
  $("#btnCancelar").show();
  $("#btnEditar").show();
}

function cancelar(){
  $("#txtUrl").val("");
  $("#txtID").val("");

  $("#btnSalvar").show();
  $("#btnCancelar").hide();
  $("#btnEditar").hide();
}

function salvarEdicao(){
  var request = new XMLHttpRequest();
  var request_GET = new XMLHttpRequest();
  var txtUrl = $("#txtUrl").val();
  var id = $("#txtID").val();

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
  }
}

function deletar(id){
  var request = new XMLHttpRequest();

  request.onreadystatechange = function(){
    if(request.readyState == 4 && request.status == 200){
      $("#mensagem").text("Excluido com sucesso!");
      listar();
    }else{
      $("#mensagem").text("Erro ao excluir!");
    }
  }

  request.open("DELETE", _URL+"/"+id, true);
  request.send();
}