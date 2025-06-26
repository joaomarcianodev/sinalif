const _URL = "http://172.16.0.9:8081/api/alarmes";
const _URLAUX = "http://172.16.0.9:8081/api/etiquetas";

$( document ).ready(function() {
    listar();
    listarEtiqueta();
});

//! View
function editar(id, txtIDEtiqueta, txtHorario, txtDias, ativado){
  $("#txtID").val(id);
  $("#selectEtiqueta").val(txtIDEtiqueta);
  $("#txtHorario").val(txtHorario);
  $("#txtDias").val(txtDias);
  $("#selectAtivo").val(ativado);

  $("#btnSalvar").hide();
  $("#btnCancelar").show();
  $("#btnEditar").show();
}

//! View
function resetar(){
  $("#txtID").val("");
  $("#selectEtiqueta").val("");
  $("#txtHorario").val("");
  $("#txtDias").val("");
  $("#selectAtivo").val("true");

  $("#btnSalvar").show();
  $("#btnCancelar").hide();
  $("#btnEditar").hide();
}

//! Função GET ETIQUETAS
function listarEtiqueta(){
  var request = new XMLHttpRequest();

  request.open("GET", _URLAUX, true);
  request.send();

  request.onreadystatechange = function(){
    if(request.readyState == 4 && request.status == 200){
      mostrarEtiquetas(JSON.parse(request.responseText));
    }
  }

  //! View
  function mostrarEtiquetas(list){
    if(list.length>0){
      out = `<option value='' selected disabled'>Selecione uma Etiqueta</option>`
      var i;
      for(i=0; i<list.length; i++){
        out += `<option value="${list[i].id_etiqueta}">${list[i].nome} (${list[i].duracao}min)</option>`
      }
    }else{
      out = "<option value='' selected disabled>Não há etiquetas cadastradas</option>"
      $("#selectEtiqueta").prop("disabled", true);
    }

    document.getElementById("selectEtiqueta").innerHTML = out;
  }
}

//! Função POST
function salvar(){
  var request = new XMLHttpRequest();
  var txtIDEtiqueta = $("#selectEtiqueta").val();
  var txtHorario = $("#txtHorario").val();
  var txtDias = $("#txtDias").val();
  var ativado = $("#selectAtivo").val();

  //* Validação
  if(txtIDEtiqueta==null || txtHorario=='' || txtDias=='' || ativado==null){
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
    "id_alarme": null,
    "id_etiqueta": txtIDEtiqueta,
    "horario_programado": txtHorario,
    "dias_semana": txtDias,
    "ativo": ativado,
    "pausado": false,
    "data_criacao": null,
    "data_modificacao": null
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
    var out = "<table border='1' class='col table table-striped'><tr><th>ID</th><th>Etiqueta</th><th>Horário</th><th>Dias</th><th>Ativo</th><th>Pausado</th><th>Data Criação</th><th>Data Modificação</th><th>Editar</th><th>Deletar</th></tr>";

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

        const date2 = new Date(list[i].data_modificacao);

        const day2 = String(date2.getDate()).padStart(2, '0');
        const month2 = String(date2.getMonth() + 1).padStart(2, '0');
        const year2 = date.getFullYear();

        const hours2 = String(date2.getHours()).padStart(2, '0');
        const minutes2 = String(date2.getMinutes()).padStart(2, '0');
        const seconds2 = String(date2.getSeconds()).padStart(2, '0');

        data_modificacao_formatado = `${day2}-${month2}-${year2} [${hours2}:${minutes2}:${seconds2}]`;

        list[i].ativo ? saidaAtivo="Sim" : saidaAtivo="Não"
        list[i].pausado ? saidaPausado="Sim" : saidaPausado="Não"

        out +=
        "<tr>"+
          "<td>"+list[i].id_alarme+"</td>"+
          "<td>"+list[i].etiqueta.nome+"</td>"+
          "<td>"+list[i].horario_programado+"</td>"+
          "<td>"+list[i].dias_semana+"</td>"+
          "<td>"+saidaAtivo+"</td>"+
          "<td>"+saidaPausado+"</td>"+
          "<td>"+data_criacao_formatado+"</td>"+
          "<td>"+data_modificacao_formatado+"</td>"+
          "<td><button class='btn btn-warning' onclick='editar("+list[i].id_alarme+", "+list[i].etiqueta.id_etiqueta+", &apos;"+list[i].horario_programado+"&apos;, &apos;"+list[i].dias_semana+"&apos;, &apos;"+list[i].ativo+"&apos;)'>Editar</button></td>"+
          "<td><button class='btn btn-danger' onclick='deletar("+list[i].id_alarme+")'>Deletar</button></td>"+
        "</tr>";
      }

      out += "</table>";
    }else{
      var out = "<div class='text-center'>Não há alarmes cadastradas.</div>"
    }

    document.getElementById("listagem").innerHTML = out;
  }
}

//! Função PUT
function salvarEdicao(){
  var request = new XMLHttpRequest();
  var request_GET = new XMLHttpRequest();
  var id = $("#txtID").val();
  var txtIDEtiqueta = $("#selectEtiqueta").val();
  var txtHorario = $("#txtHorario").val();
  var txtDias = $("#txtDias").val();
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

  function receber(alarme){
    request.open("PUT", _URL+"/"+id, true);
    request.setRequestHeader('Content-type', 'application/json');

    var json = {
      "id_etiqueta": txtIDEtiqueta,
      "horario_programado": txtHorario,
      "dias_semana": txtDias,
      "ativo": ativado
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