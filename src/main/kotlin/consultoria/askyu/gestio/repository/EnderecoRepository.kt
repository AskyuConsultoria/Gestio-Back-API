package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Endereco
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EnderecoRepository : JpaRepository<Endereco, Int> {

    fun findEndereco(): List<Endereco>

    fun findByCep(cep:String): Optional<Endereco>

    fun countByCep(cep: String): Int
}