package consultoria.askyu.gestio.dominio

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Empresa(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int,

    var cnpj:String, //modelo de CNPJ valido para testar: 00.000.000/0001-91

    var nomeEmpresa:String,

    var nomeFantasia:String,

    var telefone:Int,

    var email:String,

    var ativo:Boolean = true

)
