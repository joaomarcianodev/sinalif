const _URL = "http://localhost:8081/api/pausas";

$( document ).ready(function() {
    listar();
});

//! View
function editar(id, txtDataInicio, txtDataFim, txtHoraInicio, txtHoraFim, ativado){
  $("#txtID").val(id);
  $("#txtDataInicio").val(txtDataInicio);
  $("#txtDataFim").val(txtDataFim);
  $("#txtHoraInicio").val(txtHoraInicio);
  $("#txtHoraFim").val(txtHoraFim);
  $("#selectAtivo").val(ativado)

  $("#btnSalvar").hide();
  $("#btnCancelar").show();
  $("#btnEditar").show();
}

//! View
function resetar(){
  $("#txtID").val("");
  $("#txtDataInicio").val("");
  $("#txtDataFim").val("");
  $("#txtHoraInicio").val("");
  $("#txtHoraFim").val("");
  $("#selectAtivo").val("true");

  $("#btnSalvar").show();
  $("#btnCancelar").hide();
  $("#btnEditar").hide();
}

//! Função POST
function salvar(){
  var request = new XMLHttpRequest();
  var txtDataInicio = $("#txtDataInicio").val();
  var txtDataFim = $("#txtDataFim").val();
  var txtHoraInicio = $("#txtHoraInicio").val();
  var txtHoraFim = $("#txtHoraFim").val();
  var ativado = $("#selectAtivo").val();

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
    "id_pausa": null,
    "data_inicio": txtDataInicio,
    "data_fim": txtDataFim,
    "hora_inicio": txtHoraInicio,
    "hora_fim": txtHoraFim,
    "ativo": ativado,
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
    var out = "<table border='1' class='col table table-striped'><tr><th>ID</th><th>Data Início</th><th>Data Fim</th><th>Hora Início</th><th>Hora Fin</th><th>Ativo</th><th>Data Criação</th><th>Editar</th><th>Deletar</th></tr>";

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

        out +=
        "<tr>"+
          "<td>"+list[i].id_pausa+"</td>"+
          "<td>"+list[i].data_inicio+"</td>"+
          "<td>"+list[i].data_fim+"</td>"+
          "<td>"+list[i].hora_inicio+"</td>"+
          "<td>"+list[i].hora_fim+"</td>"+
          "<td>"+list[i].ativo+"</td>"+
          "<td>"+data_criacao_formatado+"</td>"+
          "<td><button class='btn btn-warning' onclick='editar("+list[i].id_pausa+", &apos;"+list[i].data_inicio+"&apos;, &apos;"+list[i].data_fim+"&apos;, &apos;"+list[i].hora_inicio+"&apos;, &apos;"+list[i].hora_fim+"&apos;, &apos;"+list[i].ativo+"&apos;, &apos;"+list[i].data_criacao+"&apos;)'>Editar</button></td>"+
          "<td><button class='btn btn-danger' onclick='deletar("+list[i].id_pausa+")'>Deletar</button></td>"+
        "</tr>";
      }

      out += "</table>";
    }else{
      var out = "<div class='text-center'>Não há pausas cadastradas.</div>"
    }

    document.getElementById("listagem").innerHTML = out;
  }
}

//! Função PUT
function salvarEdicao(){
  var request = new XMLHttpRequest();
  var request_GET = new XMLHttpRequest();
  var id = $("#txtID").val();
  var txtDataInicio = $("#txtDataInicio").val();
  var txtDataFim = $("#txtDataFim").val();
  var txtHoraInicio = $("#txtHoraInicio").val();
  var txtHoraFim = $("#txtHoraFim").val();
  var ativado = $("#selectAtivo").val();

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

  function receber(pausa){
    request.open("PUT", _URL+"/"+id, true);
    request.setRequestHeader('Content-type', 'application/json');

    var json = {
      "data_inicio": txtDataInicio,
      "data_fim": txtDataFim,
      "hora_inicio": txtHoraInicio,
      "hora_fim": txtHoraFim,
      "ativo": ativado,
      "data_criacao": pausa.data_criacao
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