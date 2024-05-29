package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.Usuario

data class MoradiaResponse(
    var id:Int?= null,

    var endereco: Endereco?= null,


    var usuario: Usuario?= null,

    var cliente: Cliente?= null,

    var numero:Int?= null,
    var complemento: String?= null,
    var ativo:Boolean?=null
)


