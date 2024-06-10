package consultoria.askyu.gestio.dtos




class PedidoRelatorioResponse(

    var quantidadePedidoMesPassado: Int? = null,
    var quantidadePedidoMesAtual: Int? = null
) {

    fun getPorcentagemPedido(){
      var porcentagem = quantidadePedidoMesAtual!!/quantidadePedidoMesPassado!! * 100
    }
}