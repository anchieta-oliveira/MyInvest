package com.example.myinvest.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myinvest.R
import com.example.myinvest.ativos.Ativo
import java.text.DecimalFormat

class AtivoAdapter(private val dataListAtivos: MutableList<Ativo>, private val context:Context,
                   val onClick:(Ativo) -> Unit): RecyclerView.Adapter<AtivoAdapter.AtivoViewHolder>() {

    inner class AtivoViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textCodAtivo = itemView.findViewById<TextView>(R.id.cod_ativo)
        val quantAtivo = itemView.findViewById<TextView>(R.id.item_quantidade)
        val precoAtual = itemView.findViewById<TextView>(R.id.item_preçoAtual)
        val precoMedio = itemView.findViewById<TextView>(R.id.item_preçoMedio)
        val valorTotal = itemView.findViewById<TextView>(R.id.item_total)
        val rentabilidade = itemView.findViewById<TextView>(R.id.Item_rentabilidade)


        fun bind(ativo:Ativo){
            val forDecimal = DecimalFormat("#,###.00")


            textCodAtivo.text = ativo.codAtivo
            quantAtivo.text = ativo.quantidadeAtivo.toString()
            precoAtual.text = forDecimal.format(ativo.precoAtual)
            precoMedio.text = forDecimal.format(ativo.precoMedio)
            valorTotal.text = forDecimal.format(ativo.posicaoAtual)
            rentabilidade.text = forDecimal.format(ativo.rentabilidae) + "%"

            if (ativo.rentabilidae!! > 0.0){
                rentabilidade.setTextColor(Color.GREEN)
            }else{
                rentabilidade.setTextColor(Color.RED)
            }

            with(itemView){
                setOnClickListener{
                    onClick.invoke(ativo)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtivoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycle, parent, false)
        return  AtivoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AtivoViewHolder, position: Int) {
        holder.bind(dataListAtivos[position])
    }

    override fun getItemCount() = dataListAtivos.size


    fun updateList(dataListAtivos: MutableList<Ativo>){
        this.dataListAtivos.clear()
        this.dataListAtivos.addAll(dataListAtivos)
        notifyDataSetChanged()
    }

}