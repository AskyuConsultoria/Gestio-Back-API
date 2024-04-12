package askyu.gestio.repository

import askyu.gestio.dominio.pessoa.Endereco
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EnderecoRepository : JpaRepository<Endereco, Int> {

    fun findByCep(cep:String): Optional<Endereco>
}