package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.NomeMedida
import org.springframework.data.jpa.repository.JpaRepository

interface NomeMedidaRepository: JpaRepository<NomeMedida, Int> {
   fun getByUsuarioIdAndPecaId(usuarioId: Int, pecaId: Int): List<NomeMedida>

   fun getByUsuarioIdAndPecaIdAndNomeIgnoreCase(usuarioId: Int, pecaId: Int, nome: String): List<NomeMedida>

   fun existsByUsuarioIdAndPecaIdAndId(usuarioId: Int, pecaId: Int, nomeMedidaId: Int): Boolean

}