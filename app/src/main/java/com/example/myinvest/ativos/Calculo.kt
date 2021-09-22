package com.example.myinvest.ativos

class Calculo {

    fun precoMedio(custoTotal:Double, quantidade:Int): Double? {
        // razão entre custo total e qauntidade de ativos atualemtne.
        val precoMedio = custoTotal?.div(quantidade)
        return precoMedio

    }

    fun resultado(posicaoAtual:Double, custoTotal: Double): Double? {
        // lucro ou perda é igual a o posiçãoAtual - custo total
        val resultado = posicaoAtual.minus(custoTotal)
        return resultado
    }
    fun rentabilidade(resultado: Double, custoTotal: Double): Double {
        // rentabilidade é a % de varição do ativo. Lucro (posição atual - custoTotal) duvidido pelo custoTotal x 100
        val rentabilidade = resultado.div(custoTotal)*100
        return rentabilidade

    }


    fun posicaoAtual(precoAtual:Double, quantidade: Int):Double{
        // posição que a pessoa tem no ativo, valor da cotação atual vezes quantidade que tem
        val posicaoAtual = precoAtual.times(quantidade)
        return posicaoAtual
    }

}