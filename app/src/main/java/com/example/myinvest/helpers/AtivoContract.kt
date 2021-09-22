package com.example.myinvest.helpers

import android.provider.BaseColumns

object AtivoContract {
    object AtivoEntry: BaseColumns{
        const val TABLE_NAME = "Ativos"
        const val COLUMN_NAME_CODIGO = "Codigo"
        const val COLUMN_NAME_NOME = "Nome"
        const val COLUMN_NAME_QUANTIDADE = "Quantidade"
        const val COLUMN_NAME_PRECOMEDIO = "PreçoMedio"
        //const val COLUMN_NAME_ULTIMOPRECO = "Ultimo Preço"
    }

    object Opereraca:BaseColumns{
        const val TABLE_NAME = "Operacao"
        const val COLUMN_NAME_CODIGO = "Codigo"
        const val COLUMN_NAME_TIPO = "Tipo"
        const val COLUMN_NAME_DATA = "Data"
        const val COLUMN_NAME_QUANT = "Quantidade"
        const val COLUMN_NAME_PRECO = "Preco"
    }
}