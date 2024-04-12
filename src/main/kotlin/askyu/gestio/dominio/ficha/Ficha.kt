package askyu.gestio.dominio.ficha

import askyu.gestio.dominio.pessoa.Pessoa
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class Ficha(

    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idFicha:Int?,
    @field:NotBlank @ManyToOne var fkPessoa: Pessoa?,
    @field:NotBlank @ManyToOne var fkModelo: Modelo?,
    @field:NotBlank var material:String?,
    @field:NotBlank var observacao:String?,
    @field:NotBlank var ativo:Boolean?


){
    constructor() : this(null, null, null,null, null, null)
}
