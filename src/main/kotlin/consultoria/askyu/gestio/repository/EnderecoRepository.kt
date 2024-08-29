package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EnderecoRepository : JpaRepository<Endereco, Int>, IRepositorio {

    fun findByCep(cep:String): Optional<Endereco>

    fun countByCep(cep: String): Int
}