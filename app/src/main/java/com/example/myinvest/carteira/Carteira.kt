package com.example.myinvest.carteira

import com.example.myinvest.ativos.Ativo
import com.example.myinvest.ativos.Calculo

class Carteira {

    fun getRentabilidade(dataAtivos:MutableList<Ativo>): Double {
        val custoCarteira = dataAtivos.sumOf { it.custoTotal!! }
        val resultadoCarteira = getresultadoCarteira(dataAtivos)

        val rentabilidadeCarteira = Calculo().rentabilidade(resultadoCarteira, custoCarteira)
        return rentabilidadeCarteira
    }

    fun getposicaoAtual(dataAtivos: MutableList<Ativo>): Double {
        val posicaoCarteira = dataAtivos.sumOf { it.posicaoAtual!! }
        return posicaoCarteira

    }

    fun getresultadoCarteira(dataAtivos: MutableList<Ativo>): Double {
        val resultadoCarteira = dataAtivos.sumOf { it.resultado!! }
        return resultadoCarteira

    }
}