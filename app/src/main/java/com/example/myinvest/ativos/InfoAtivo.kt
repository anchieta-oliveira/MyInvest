package com.example.myinvest.ativos

import android.util.Log
import com.example.myinvest.application.MyInvestApplication
import org.json.JSONObject

class InfoAtivo {


    fun getNome(cod: String): String? {
        //vai ter que usar o de busca

        val dataNativo = ConectionApi().infoAtivo(cod)
        val nomeAtivo = dataNativo?.getString("2. name")

        return nomeAtivo
    }


    fun getPrecoAtual(cod: String): Double? {
       try {
                val textNativo = ConectionApi().conectionDay(cod)
                val dataNativo = JSONObject(textNativo)
                val dataMeta = dataNativo.getJSONObject("Meta Data")
                val dataUltima = dataMeta.getString("3. Last Refreshed")
                val dataTime = dataNativo.getJSONObject("Time Series (Daily)")
                val dataObjUltimo = dataTime.getJSONObject("$dataUltima")
                val ultimoPreco = dataObjUltimo.getString("4. close")
                return ultimoPreco.toDouble()

       }catch (e: Exception){return null }


    }



    fun getPrecoMedio(cod: String): Double? {
        // razão entre custo total e qauntidade de ativos atualemtne.
        val precoMedio = getCustoTotal(cod)?.div(getQuantidade(cod))
        return precoMedio

    }

    fun getResultado(cod: String): Double? {
        // lucro ou perda é igual a o posiçãoAtual - custo total
        val resultado = getPosicaoAtual(cod)?.minus(getCustoTotal(cod)!!)
        return resultado
        Log.i("resul", resultado.toString())
    }

    fun getRentabilidade(cod:String): Double? {
        // rentabilidade é a % de varição do ativo. Lucro (posição atual - custoTotal) duvidido pelo custoTotal x 100
        val rentabilidade = getResultado(cod)?.div(getCustoTotal(cod)!!)!!*100
        return rentabilidade

    }


    fun getPosicaoAtual(cod: String):Double?{
        // posição que a pessoa tem no ativo, valor da cotação atual vezes quantidade que tem
        val posicaoAtual = getPrecoAtual(cod)?.times(getQuantidade(cod))
        return posicaoAtual
    }

    fun getCustoTotal(cod: String): Double? {
        // soma de todas as compras subitraido pelas vendas
        val listaOpera = MyInvestApplication.instance.helperDBAtivos?.getOpera(cod) ?: mutableListOf()
        val quantCompra = listaOpera.filter { it.tipo == "COMPRA"}.sumOf { it.preco * it.quantidade}
        val quantVenda = listaOpera.filter { it.tipo == "VENDA"  }.sumOf { it.preco * it.quantidade }
        val custoTotal = quantCompra - quantVenda
        return custoTotal
    }

    fun getQuantidade(cod:String): Int {
        // quantidade de ativos que tem atualemtne na carteira. É o n de ativos comprados menos os vendidos
        val listaOpera = MyInvestApplication.instance.helperDBAtivos?.getOpera(cod) ?: mutableListOf()
        val quantCompra = listaOpera.filter { it.tipo == "COMPRA"}.sumOf { it.quantidade }
        val quantVenda = listaOpera.filter { it.tipo == "VENDA"  }.sumOf { it.quantidade }
        val quantidade = quantCompra - quantVenda
        return quantidade
    }



    fun getAtivo(cod: String): Ativo {
        val nome = getNome(cod)
        val precoAtual = getPrecoAtual(cod)
        val quantidadeAtivo = getQuantidade(cod)
        val custoTotal = getCustoTotal(cod)
        val precoMedio = Calculo().precoMedio(custoTotal!!, quantidadeAtivo)
        val posicaoAtual = Calculo().posicaoAtual(precoAtual!!, quantidadeAtivo)
        val resultado = Calculo().resultado(posicaoAtual, custoTotal)
        val rentabilidae = Calculo().rentabilidade(resultado!!, custoTotal)

        val ativo = Ativo(cod, nome, quantidadeAtivo, precoMedio,
            precoAtual, rentabilidae, posicaoAtual, custoTotal, resultado)

        return ativo

    }

}
