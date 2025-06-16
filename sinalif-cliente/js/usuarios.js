const _URL = "http://localhost:8081/api/usuarios";

$( document ).ready(function() {
    listar();
});

//! View
function editar(id, txtNome, txtEmail, txtSenha, txtIDPerfil){
  $("#txtID").val(id);
  $("#txtNome").val(txtNome);
  $("#txtEmail").val(txtEmail);
  $("#txtSenha").val(txtSenha);
  $("#selectPerfil").val(txtIDPerfil);

  $("#btnSalvar").hide();
  $("#btnCancelar").show();
  $("#btnEditar").show();
}

//! View
function resetar(){
  $("#txtID").val("");
  $("#txtNome").val("");
  $("#txtEmail").val("");
  $("#txtSenha").val("");
  $("#selectPerfil").val("");

  $("#btnSalvar").show();
  $("#btnCancelar").hide();
  $("#btnEditar").hide();
}

//! FUNÇÃO POST
function salvar(){
  var request = new XMLHttpRequest();
  var txtNome = $("#txtNome").val();
  var txtEmail = $("#txtEmail").val();
  var txtSenha = $("#txtSenha").val();
  var txtIDPerfil = $("#selectPerfil").val();

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
    "nome": txtNome,
    "email": txtEmail,
    "senha": txtSenha,
    "id_perfil": txtIDPerfil,
    "data_criacao": null,
    "sugestoes": []
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
    var out = "<table border='1' class='col table table-striped'><tr><th>ID</th><th>ID Perfil</th><th>Nome</th><th>Email</th><th>Senha</th><th>Data Criação</th><th>Qtd Sugestões</th><th>Editar</th><th>Deletar</th></tr>";

    if(list.length>0){
      var i;
      for(i=0; i<list.length; i++){
        out +=
        "<tr>"+
          "<td>"+list[i].id_usuario+"</td>"+
          "<td>"+list[i].id_perfil+"</td>"+
          "<td>"+list[i].nome+"</td>"+
          "<td>"+list[i].email+"</td>"+
          "<td>"+list[i].senha+"</td>"+
          "<td>"+list[i].data_criacao+"</td>"+
          "<td>"+list[i].sugestoes.length+"</td>"+
          "<td><button class='btn btn-warning' onclick='editar(&apos;"+list[i].id_usuario+"&apos;, &apos;"+list[i].nome+"&apos;, &apos;"+list[i].email+"&apos;, &apos;"+list[i].senha+"&apos;, &apos;"+list[i].id_perfil+"&apos;)'>Editar</button></td>"+
          "<td><button class='btn btn-danger' onclick='deletar(&apos;"+list[i].id_usuario+"&apos;)'>Deletar</button></td>"+
        "</tr>";
      }

      out += "</table>";
    }else{
      var out = "<div class='text-center'>Não há usuários cadastrados.</div>"
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
  var txtEmail = $("#txtEmail").val();
  var txtSenha = $("#txtSenha").val();
  var txtIDPerfil = $("#selectPerfil").val();

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

  function receber(usuario){
    request.open("PUT", _URL+"/"+id, true);
    request.setRequestHeader('Content-type', 'application/json');

    var json = {
      "id_usuario": id,
      "nome": txtNome,
      "email": txtEmail,
      "senha": txtSenha,
      "id_perfil": txtIDPerfil,
      "data_criacao": usuario.data_criacao,
      "sugestoes": []
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