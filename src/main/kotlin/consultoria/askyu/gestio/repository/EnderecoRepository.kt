package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Endereco
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EnderecoRepository : JpaRepository<Endereco, Int> {

    fun findByCep(cep:String): Optional<Endereco>

    fun existsByUsuarioIdAndId(idUsuario: Int, id: Int): Boolean

    fun countByCep(cep: String): Int

    fun deleteByCep(cep:String): Void

    fun existsByCep(cep: String): Boolean
}