const _URL = "http://localhost:8080/api/musicas";

$(document).ready(function() {
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

  //* Validação
  if(txtUrl==''){
    $("#mensagem").text("Dados inválidos");
    listar();
    return
  }

  request.onreadystatechange = function(){
    if(request.readyState == 4 && request.status == 200){
      $("#mensagem").text("Salvo com sucesso!");
      listar();
    }else{
      $("#mensagem").text("Erro ao salvar.");
      listar();
    }
  }

  request.open("POST", _URL, true);
  request.setRequestHeader('Content-type', 'application/json');

  var json = {
    "id_musica": null,
    "url": txtUrl,
    "status": null,
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
    var out = "<table border='1' class='col table table-striped'><tr><th>ID</th><th>URL</th><th>Status</th><th>Data Criação</th><th>Data Sugestão</th><th>Editar</th><th>Deletar</th></tr>";

    if(list.length>0){
      var i;
      for(i=0; i<list.length; i++){
        const date = new Date(list[i].data_criacao);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');
        data_criacao_formatado = `${day}-${month}-${year} [${hours}:${minutes}:${seconds}]`;

        if(list[i].data_sugestao!=null){
          const date2 = new Date(list[i].data_sugestao);
          const day2 = String(date2.getDate()).padStart(2, '0');
          const month2 = String(date2.getMonth() + 1).padStart(2, '0');
          const year2 = date2.getFullYear();
          const hours2 = String(date2.getHours()).padStart(2, '0');
          const minutes2 = String(date2.getMinutes()).padStart(2, '0');
          const seconds2 = String(date2.getSeconds()).padStart(2, '0');
          data_sugestao_formatado = `${day2}-${month2}-${year2} [${hours2}:${minutes2}:${seconds2}]`;
        }else{
          data_sugestao_formatado = "Cadastro Manual"
        }
        

        out +=
        "<tr>"+
          "<td>"+list[i].id_musica+"</td>"+
          "<td>"+list[i].url+"</td>"+
          "<td>"+list[i].status+"</td>"+
          "<td>"+data_criacao_formatado+"</td>"+
          "<td>"+data_sugestao_formatado+"</td>"+
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