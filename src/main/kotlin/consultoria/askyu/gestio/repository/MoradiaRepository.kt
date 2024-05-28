package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.Moradia
import consultoria.askyu.gestio.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MoradiaRepository: JpaRepository<Moradia, Int> {
    fun findMoradia():List<Moradia>

    fun findByCliente(cliente: Cliente): Optional<Moradia>

    fun findByUsuario(usuario: Usuario): Optional<Moradia>

    fun findByEndereco(endereco: Endereco): Optional<Moradia>


}