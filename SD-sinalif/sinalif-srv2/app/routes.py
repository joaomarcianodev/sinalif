from flask import Blueprint, request, jsonify
from bson.objectid import ObjectId
from app.database import mongo
from app.models import Usuario
from app.models import Sugestao
import datetime

api_bp = Blueprint("api", __name__)

#---------------#
#   USUARIOS    #
#---------------#

# CREATE
@api_bp.route("/usuarios", methods=["POST"])
def create_usuario():
    data = request.json
    usuario = Usuario.to_dict(data) # type: ignore
    usuario["data_criacao"] = datetime.datetime.now().strftime("%d-%m-%Y [%H:%M:%S]");
    result = mongo.db.usuarios.insert_one(usuario) # type: ignore
    return jsonify({"id_usuario": str(result.inserted_id), **usuario}), 200

# GET
@api_bp.route("/usuarios", methods=["GET"])
def list_usuario():
    usuarios = mongo.db.usuarios.find() # type: ignore
    return jsonify([Usuario.to_dict(usuario) for usuario in usuarios])

# DETAIL
@api_bp.route("/usuarios/<string:id_usuario>", methods=["GET"])
def detail_usuario(id_usuario):
    usuario = mongo.db.usuarios.find_one({"_id": ObjectId(id_usuario)})# type: ignore
    if usuario:
        return jsonify(Usuario.to_dict(usuario))
    return jsonify({"error": "Usuario not found"}), 404

# UPDATE
@api_bp.route("/usuarios/<string:id_usuario>", methods=["PUT"])
def update_usuario(id_usuario):
    data = request.json
    update_data = {"$set": Usuario.to_dict(data)}
    result = mongo.db.usuarios.update_one({"_id": ObjectId(id_usuario)}, update_data)# type: ignore

    if result.matched_count:
        updated_Usuario = mongo.db.usuarios.find_one({"_id": ObjectId(id_usuario)})# type: ignore
        return jsonify(Usuario.to_dict(updated_Usuario))
    return jsonify({"error": "Usuario not found"}), 404

# DELETE
@api_bp.route("/usuarios/<string:id_usuario>", methods=["DELETE"])
def delete_usuario(id_usuario):
    result = mongo.db.usuarios.delete_one({"_id": ObjectId(id_usuario)})# type: ignore
    if result.deleted_count:
        return jsonify({"message": "Usuario deleted successfully"})
    return jsonify({"error": "Usuario not found"}), 404



#---------------#
#   SUGESTAO    #
#---------------#

# CREATE
@api_bp.route("/sugestao", methods=["POST"])
def create_sugestao():
    data = request.json
    sugestao = Sugestao.to_dict(data) # type: ignore
    sugestao["status_sugestao"] = "pendente"
    sugestao["data_analise"] = "aguardando"
    sugestao["data_sugestao"] = datetime.datetime.now().strftime("%d-%m-%Y [%H:%M:%S]")
    result = mongo.db.sugestoes.insert_one(sugestao) # type: ignore
    return jsonify({"id_sugestao": str(result.inserted_id), **sugestao}), 200

# GET
@api_bp.route("/sugestao", methods=["GET"])
def list_sugestao():
    sugestoes = mongo.db.sugestoes.find() # type: ignore
    return jsonify([Sugestao.to_dict(sugestao) for sugestao in sugestoes])

# DETAIL
@api_bp.route("/sugestao/<string:id_sugestao>", methods=["GET"])
def detail_sugestao(id_sugestao):
    sugestao = mongo.db.sugestoes.find_one({"_id": ObjectId(id_sugestao)})# type: ignore
    if sugestao:
        return jsonify(Sugestao.to_dict(sugestao))
    return jsonify({"error": "Sugestao not found"}), 404

# UPDATE
@api_bp.route("/sugestao/<string:id_sugestao>", methods=["PUT"])
def update_sugestao(id_sugestao):
    data = request.json
    update_data = {"$set": Sugestao.to_dict(data)}
    result = mongo.db.sugestoes.update_one({"_id": ObjectId(id_sugestao)}, update_data)# type: ignore

    if result.matched_count:
        updated_Sugestao = mongo.db.sugestoes.find_one({"_id": ObjectId(id_sugestao)})# type: ignore
        return jsonify(Sugestao.to_dict(updated_Sugestao))
    return jsonify({"error": "Sugestao not found"}), 404

# DELETE
@api_bp.route("/sugestao/<string:id_sugestao>", methods=["DELETE"])
def delete_sugestao(id_sugestao):
    result = mongo.db.sugestoes.delete_one({"_id": ObjectId(id_sugestao)})# type: ignore
    if result.deleted_count:
        return jsonify({"message": "Sugestao deleted successfully"})
    return jsonify({"error": "Sugestao not found"}), 404