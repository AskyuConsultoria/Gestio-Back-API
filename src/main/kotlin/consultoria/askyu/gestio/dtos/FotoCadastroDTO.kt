package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.ItemPedido
import consultoria.askyu.gestio.dominio.Usuario
import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull


data class FotoCadastroDTO(
    @NotBlank  (message = "Adicione nome a foto ")
    var nomeArquivo:String? = null,
    var dadoArquivo: ByteArray? = null,
    @NotNull
    var usuario: Int? = null,
    @NotNull
    var itemPedido:Int? = null
)