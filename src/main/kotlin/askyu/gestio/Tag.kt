package askyu.gestio

class Tag(var nome:String, val atualizar:Boolean) {
    var prioridade:Int = 0
        set(value) {
            if(value >= 10){
                field = 10
            } else if (value <= 0){
                field = 0
            } else {
                field = value
            }
        }
}