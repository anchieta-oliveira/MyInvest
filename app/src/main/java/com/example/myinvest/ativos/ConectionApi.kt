package com.example.myinvest.ativos

import org.json.JSONObject
import java.net.URL

class ConectionApi {
    private val API_KEY = "apikey=HKQ27HHBUGOGFPQ5"
    //HKQ27HHBUGOGFPQ5
    //58EBYFO7NOKH2X5Z
    //MZHD9716ZAP8ATAT

    private var URL = "https://www.alphavantage.co/query?"

    fun conectionIntraDay(min:Int, codAtivo: String): String? {

        val function = "function=TIME_SERIES_INTRADAY&"
        val symbol = "symbol=${codAtivo}&"
        val interval = "interval=${min}min&"
        val linkApi = URL + function + symbol + interval + API_KEY

        return try {

            val resultado = URL(linkApi).readText()

            return resultado

        } catch (e: Exception) {
            return null
        }
    }

    fun conectionDay(codAtivo: String): String? {
        //https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&apikey=demo
        val function = "function=TIME_SERIES_DAILY&"
        val symbol = "symbol=${codAtivo}&"
        val linkApi = URL + function + symbol + API_KEY

        return try {

            val resultado = URL(linkApi).readText()

            return resultado

        } catch (e: Exception) {
            return null
        }

    }


    fun infoAtivo(codAtivo: String): JSONObject? {
        val function = "function=SYMBOL_SEARCH&"
        val keywords = "keywords=${codAtivo}&"
        val linkApi = URL + function + keywords + API_KEY

        try {
            var resultado = URL(linkApi).readText()
            val x = 1

            val dataNativo = JSONObject(resultado)
            val dataBest = dataNativo.getJSONArray("bestMatches")
            val infosAtivo = dataBest.getJSONObject(0)
            return infosAtivo

        }catch (e: Exception){return JSONObject()}
    }


    fun pesquisaAtivo(pesquisa:String?): MutableList<Ativo> {

        // https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=petr&apikey=HKQ27HHBUGOGFPQ5

        val function = "function=SYMBOL_SEARCH&"
        val keywords = "keywords=${pesquisa}&"
        val linkApi = URL + function + keywords + API_KEY

        try {
            var resultado = URL(linkApi).readText()

            val dataNativo = JSONObject(resultado)
            val dataBest = dataNativo.getJSONArray("bestMatches")
            var listaBusca = mutableListOf<Ativo>()

            for (i in 0 until dataBest.length()) {
                val item = dataBest.getJSONObject(i)
                val ativo = Ativo(
                    item.getString("1. symbol"),
                    item.getString("2. name"))

                listaBusca.add(ativo)

            }
            return listaBusca

        }catch (e:Exception){return mutableListOf()}

    }
}
