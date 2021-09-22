package com.example.myinvest

import com.example.myinvest.ativos.Ativo

data class Datagrafico (val cod:String,
val posicaoAtual:Double)

fun main() {

    val data = mutableListOf<Ativo>()
    val d = Ativo("CA", "s", 10, 10.0, 6.9, 10.0, 100.0, 102.0, 20.9,)
    val b = Ativo("aa2s", "s", 10, 10.0, 6.9, 10.0, 100.0, 102.0, 20.9,)
    val asd = Ativo("3", "s", 10, 10.0, 6.9, 10.0, 100.0, 102.0, 20.9,)
    data.add(d)
    data.add(b)
    data.add(asd)

    fun dataGrafico(): Array<Any> {


        val dataArry = arrayListOf<Any>()
        for (i in 0 until data.size) {
            val novoArry = arrayOf(data[i].codAtivo, data[i].posicaoAtual)
            dataArry += novoArry
        }
        return dataArry.toArray()
    }

    val x = dataGrafico()
    val y = x
}


/*
return arrayOf(
arrayOf("Swift", 999),
arrayOf("Python", 83),
arrayOf("OC",     11),
arrayOf("Go",     30))
*/
