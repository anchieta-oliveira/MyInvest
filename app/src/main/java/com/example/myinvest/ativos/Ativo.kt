package com.example.myinvest.ativos

import android.os.Parcel
import android.os.Parcelable


data class Ativo(val codAtivo:String,
                 var nome:String?=null,
                 var quantidadeAtivo:Int? = null,
                 var precoMedio:Double?=null,
                 var precoAtual:Double?=null,
                 var rentabilidae:Double?=null,
                 var posicaoAtual:Double?=null,
                 var custoTotal:Double?=null,
                 var resultado:Double?=null
            ): Parcelable

{

    fun setInfoAtivo(){
        //this.nome = InfoAtivo().getNome(codAtivo)
        this.precoAtual = InfoAtivo().getPrecoAtual(codAtivo)
        this.quantidadeAtivo = InfoAtivo().getQuantidade(codAtivo)
        this.custoTotal = InfoAtivo().getCustoTotal(codAtivo)
        this.precoMedio = Calculo().precoMedio(custoTotal!!, quantidadeAtivo!!)
        this.posicaoAtual = Calculo().posicaoAtual(precoAtual!!, quantidadeAtivo!!)
        this.resultado = Calculo().resultado(posicaoAtual!!, custoTotal!!)
        this.rentabilidae = Calculo().rentabilidade(resultado!!, custoTotal!!)

    }

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    ) {
    }



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(codAtivo)
        parcel.writeString(nome)
        parcel.writeValue(quantidadeAtivo)
        parcel.writeValue(precoMedio)
        parcel.writeValue(precoAtual)
        parcel.writeValue(rentabilidae)
        parcel.writeValue(posicaoAtual)
        parcel.writeValue(custoTotal)
        parcel.writeValue(resultado)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ativo> {
        override fun createFromParcel(parcel: Parcel): Ativo {
            return Ativo(parcel)
        }

        override fun newArray(size: Int): Array<Ativo?> {
            return arrayOfNulls(size)
        }
    }
}
