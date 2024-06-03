package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.TipoTelefone
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TipoTelefoneRepository: JpaRepository<TipoTelefone, Int> {

    fun findByDigitos(digitos:Int): List<TipoTelefone>

    fun findByUsuarioId(usuarioId:Int): List<TipoTelefone>

    fun existsByUsuarioIdAndId(usuarioId: Int, tipoTelefoneId: Int): Boolean

    fun findByUsuarioIdAndId(usuarioId: Int, tipoTelefoneId: Int): TipoTelefone

}