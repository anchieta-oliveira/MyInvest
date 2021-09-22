package com.example.myinvest.graficos

import com.example.myinvest.ativos.Ativo
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATooltip
import java.text.DecimalFormat

class GraficoPizza() {
    val forDecimal = DecimalFormat("#,###.00")
    fun dataGrafico(data: MutableList<Ativo>): Array<Any> {
        val dataArry = arrayListOf<Any>()
        for (i in 0 until data.size) {
            val novoArry = arrayOf(data[i].codAtivo, data[i].posicaoAtual)
            dataArry += novoArry
        }
        return dataArry.toArray()

    }


    fun graficoPizzaAtivo(data: MutableList<Ativo>): AAChartModel {
        val aaTooltip = AATooltip().valueDecimals(2)

        val graficoPizza = AAChartModel()
            .chartType(AAChartType.Pie)
            .backgroundColor("#F8F8FA")
            //.dataLabelsEnabled(true)
            .yAxisTitle("℃")
            .legendEnabled(false)
            .series(arrayOf(
                AASeriesElement()
                    .name("Valor R$")
                    .tooltip(aaTooltip)
                    .borderWidth(0f)
                    .lineWidth(0f)
                    .innerSize("60%")
                    .size("60%")
                    .data(dataGrafico(data)
                    )))

        return graficoPizza
    }


}

