package consultoria.askyu.gestio.interfaces

import jakarta.persistence.EntityManager

abstract class RelatorioServico(
    val relatorioEntityManager: EntityManager
)