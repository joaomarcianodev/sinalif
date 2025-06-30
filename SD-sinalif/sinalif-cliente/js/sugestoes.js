const _URL = "http://localhost:8081/api/sugestao";
const _URLAUX = "http://localhost:8081/api/usuarios";

$( document ).ready(function() {
  listar();
  listarUsuarios();
});

//! View
function editar(id, txtIDUsuario, txtIDMusica, txtUrl){
  $("#txtID").val(id);
  $("#selectUsuario").val(txtIDUsuario);
  $("#txtUrl").val(txtUrl);
  //$("#txtIDMusica").val(txtIDMusica);

  $("#btnSalvar").hide();
  $("#btnCancelar").show();
  $("#btnEditar").show();
}

//! View
function resetar(){
  $("#txtID").val("");
  $("#selectUsuario").val("");
  $("#txtUrl").val("");
  //$("#txtIDMusica").val("");

  $("#btnSalvar").show();
  $("#btnCancelar").hide();
  $("#btnEditar").hide();
}

//! Função GET USUARIOS
function listarUsuarios(){
  var request = new XMLHttpRequest();

  request.open("GET", _URLAUX, true);
  request.send();

  request.onreadystatechange = function(){
    if(request.readyState == 4 && request.status == 200){
      mostrarUsuarios(JSON.parse(request.responseText));
    }
  }

  //! View
  function mostrarUsuarios(list){
    if(list.length>0){
      out = `<option value='' selected disabled>Selecione um Usuário</option>`
      var i;
      for(i=0; i<list.length; i++){
        out += `<option value="${list[i].id_usuario}">${list[i].nome}</option>`
      }
    }else{
      out = "<option value='' selected disabled>Não há usuários cadastradas</option>"
      $("#selectUsuario").prop("disabled", true);
    }

    document.getElementById("selectUsuario").innerHTML = out;
  }
}

//! FUNÇÃO POST
function salvar(){
  var request = new XMLHttpRequest();
  var txtIDUsuario = $("#selectUsuario").val();
  var txtUrl = $("#txtUrl").val();
  //var txtIDMusica = $("#txtIDMusica").val();

  //* Validação
  if(txtIDUsuario=='' || txtUrl==''){
    $("#mensagem").text("Dados inválidos");
    listar();
    return
  }

  request.onreadystatechange = function(){
    if(request.readyState == 4){
      //aqui o && request.status == 200 no if está dando problema pois o status está sendo = 0 (???)
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
    "id_usuario": txtIDUsuario,
    "url_sugerida": txtUrl,
    "id_musica": null,
    "status_sugestao": null,
    "data_sugestao": null,
    "data_analise": null
  }
  
  request.send(JSON.stringify(json));
  resetar();
}

//! Função GET
function listar(){
  var request = new XMLHttpRequest();
  
  request.open("GET", _URL, true);
  //request.setRequestHeader('Content-type', 'application/json');
  request.send();

  request.onreadystatechange = function(){
    if(request.readyState == 4 && request.status == 200){
      mostrar(JSON.parse(request.responseText));
    }
  }

  //! View
  function mostrar(list){
    var out = "<table border='1' class='col table table-striped'><tr><th>ID</th><th>ID Usuário</th><th>ID Música</th><th>URL</th><th>Status</th><th>Data Criação</th><th>Data Análise</th><th>Editar</th><th>Deletar</th></tr>";

    if(list.length>0){
      var i;
      for(i=0; i<list.length; i++){
        out +=
        "<tr>"+
          "<td>"+list[i].id_sugestao+"</td>"+
          "<td>"+list[i].id_usuario+"</td>"+
          "<td>"+list[i].id_musica+"</td>"+
          "<td>"+list[i].url_sugerida+"</td>"+
          "<td>"+list[i].status_sugestao+"</td>"+
          "<td>"+list[i].data_sugestao+"</td>"+
          "<td>"+list[i].data_analise+"</td>"+
          "<td><button class='btn btn-warning' onclick='editar(&apos;"+list[i].id_sugestao+"&apos;, &apos;"+list[i].id_usuario+"&apos;, &apos;"+list[i].id_musica+"&apos;, &apos;"+list[i].url_sugerida+"&apos;, &apos;"+list[i].status_sugestao+"&apos;)'>Editar</button></td>"+
          "<td><button class='btn btn-danger' onclick='deletar(&apos;"+list[i].id_sugestao+"&apos;)'>Deletar</button></td>"+
        "</tr>";
      }

      out += "</table>";
    }else{
      var out = "<div class='text-center'>Não há sugestões cadastradas.</div>"
    }

    document.getElementById("listagem").innerHTML = out;
  }
}

//! Função PUT
function salvarEdicao(){
  var request = new XMLHttpRequest();
  var request_GET = new XMLHttpRequest();
  var id = $("#txtID").val();
  var txtIDUsuario = $("#selectUsuario").val();
  var txtUrl = $("#txtUrl").val();
  //var txtIDMusica = $("#txtIDMusica").val();

  request.onreadystatechange = function(){
    if(request.readyState == 4){
      //aqui o request.status == 200 tmb estava dando problema, mesma questa. status = 0
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

  function receber(sugestao){
    request.open("PUT", _URL+"/"+id, true);
    request.setRequestHeader('Content-type', 'application/json');
    
    var json = {
      "id_usuario": txtIDUsuario,
      "url_sugerida": txtUrl,
      "id_musica": sugestao.id_musica,
      "status_sugestao": sugestao.status_sugestao,
      "data_sugestao": sugestao.data_sugestao,
      "data_analise": sugestao.data_analise
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