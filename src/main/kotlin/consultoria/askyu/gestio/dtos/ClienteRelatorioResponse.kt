package consultoria.askyu.gestio.dtos

data class ClienteRelatorioResponse(
    var quantidadeClienteMesPassado: Int? = null,
    var quantidadeClienteMesAtual: Int? = null
) {
    fun getPorcentagemCliente(){
        var porcentagem = quantidadeClienteMesAtual!!/quantidadeClienteMesPassado!! * 100
    }
}


