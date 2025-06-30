const _URL = "http://localhost:8081/api/perfis";

$( document ).ready(function() {
    listar();
});

//! View
function editar(id, txtNome){
  $("#txtID").val(id);
  $("#txtNome").val(txtNome);

  $("#btnSalvar").hide();
  $("#btnCancelar").show();
  $("#btnEditar").show();
}

//! View
function resetar(){
  $("#txtID").val("");
  $("#txtNome").val("");

  $("#btnSalvar").show();
  $("#btnCancelar").hide();
  $("#btnEditar").hide();
}

//! Função POST
function salvar(){
  var request = new XMLHttpRequest();
  var txtNome = $("#txtNome").val();

  //* Validação
  if(txtNome==''){
    $("#mensagem").text("Dados inválidos");
    listar();
    return
  }

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
    "id_perfil": null,
    "nome": txtNome
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
    var out = "<table border='1' class='col table table-striped'><tr><th>ID</th><th>Nome</th><th>Editar</th><th>Deletar</th></tr>";

    if(list.length>0){
      var i;
      for(i=0; i<list.length; i++){
        out +=
        "<tr>"+
          "<td>"+list[i].id_perfil+"</td>"+
          "<td>"+list[i].nome+"</td>"+
          "<td><button class='btn btn-warning' onclick='editar("+list[i].id_perfil+", &apos;"+list[i].nome+"&apos;)'>Editar</button></td>"+
          "<td><button class='btn btn-danger' onclick='deletar("+list[i].id_perfil+")'>Deletar</button></td>"+
        "</tr>";
      }

      out += "</table>";
    }else{
      var out = "<div class='text-center'>Não há perfis cadastradas.</div>"
    }

    document.getElementById("listagem").innerHTML = out;
  }
}

//! Função PUT
function salvarEdicao(){
  var request = new XMLHttpRequest();
  var request_GET = new XMLHttpRequest();
  var id = $("#txtID").val();
  var txtNome = $("#txtNome").val();

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

  function receber(perfil){
    request.open("PUT", _URL+"/"+id, true);
    request.setRequestHeader('Content-type', 'application/json');

    var json = {
      "nome": txtNome
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