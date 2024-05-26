package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Cliente
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ClienteRepository: JpaRepository<Cliente, Int> {

    fun findCliente(): List<Cliente>

    fun findByNome(nome: String): List<Cliente>
}