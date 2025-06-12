from app.database import mongo

class Usuario:
    @staticmethod
    def to_dict(usuario):

        if usuario.get("_id", "") == '':
            return {
                "id_perfil": usuario["id_perfil"],
                "nome": usuario["nome"],
                "email": usuario.get("email", ""),
                "senha": usuario["senha"],
                "data_criacao": usuario["data_criacao"],
                "sugestoes": usuario["sugestoes"],
            }
        else:
            return {
                "id_usuario": str(usuario["_id"]),
                "id_perfil": usuario["id_perfil"],
                "nome": usuario["nome"],
                "email": usuario["email"],
                "senha": usuario["senha"],
                "data_criacao": usuario["data_criacao"],
                "sugestoes": usuario["sugestoes"],
            }

class Sugestao:
    @staticmethod
    def to_dict(sugestao):

        if sugestao.get("_id", "") == '':
            return {
                "id_usuario": sugestao["id_usuario"],
                "url_sugerida": sugestao["url_sugerida"],
                "status_sugestao": sugestao["status_sugestao"],
                "data_sugestao": sugestao["data_sugestao"],
                "data_analise": sugestao.get("data_analise", ""),
                "id_musica": sugestao["id_musica"],
            }
        else:
            return {
                "id_sugestao": str(sugestao["_id"]),
                "id_usuario": sugestao["id_usuario"],
                "url_sugerida": sugestao["url_sugerida"],
                "status_sugestao": sugestao["status_sugestao"],
                "data_sugestao": sugestao["data_sugestao"],
                "data_analise": sugestao.get("data_analise", ""),
                "id_musica": sugestao["id_musica"],
            }