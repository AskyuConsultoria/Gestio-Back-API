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
    var itemPedido:Int? = null,
    var usuario:Int? = null


    )