package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull


@Entity
data class Foto(

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,
    var nomeArquivo:String? = null,
    var dadoArquivo: ByteArray? = null,
    @field:ManyToOne
    var itemPedido:ItemPedido? = null,
    @field:ManyToOne
    var usuario:Usuario? = null


    )