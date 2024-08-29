package consultoria.askyu.gestio.interfaces

import org.modelmapper.ModelMapper

abstract class Servico(
    val servicoRepository: IRepositorio,
    val servicoMapper: ModelMapper = ModelMapper()
)