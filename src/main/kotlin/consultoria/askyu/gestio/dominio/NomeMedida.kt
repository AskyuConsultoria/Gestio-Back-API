package consultoria.askyu.gestio.dominio

import jakarta.persistence.*

@Entity
data class NomeMedida(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var nome: String? = null,

    @ManyToOne
    var peca: Peca? = null,

    @ManyToOne
    var usuario: Usuario? = null,

    var ativo: Boolean? = true
){
    constructor(
        paramNome: String?,
        paramPeca: Peca?,
        paramUsuario: Usuario?
    ): this(nome = paramNome, peca = paramPeca, usuario = paramUsuario)
}