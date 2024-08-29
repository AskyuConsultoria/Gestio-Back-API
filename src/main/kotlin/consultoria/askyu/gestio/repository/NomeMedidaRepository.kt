package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.NomeMedida
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository

interface NomeMedidaRepository: JpaRepository<NomeMedida, Int>, IRepositorio {
   fun getByUsuarioIdAndPecaIdAndAtivoIsTrue(usuarioId: Int, pecaId: Int): List<NomeMedida>

   fun getByUsuarioIdAndPecaIdAndNomeContainsIgnoreCaseAndAtivoIsTrue(usuarioId: Int, pecaId: Int, nome: String): List<NomeMedida>

   fun getByUsuarioIdAndPecaIdAndIdAndAtivoIsTrue(usuarioId: Int, pecaId: Int, nomeMedidaId: Int): NomeMedida

   fun existsByUsuarioIdAndPecaIdAndIdAndAtivoIsTrue(usuarioId: Int, pecaId: Int, nomeMedidaId: Int): Boolean

}