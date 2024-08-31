package consultoria.askyu.gestio.interfaces

abstract class ViewServico(
    val viewRepository: IRepositorio,
    val viewService: Servico
){
    abstract fun visualizar(usuarioId: Int): Any
}