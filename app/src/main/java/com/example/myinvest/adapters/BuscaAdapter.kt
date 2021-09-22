package com.example.myinvest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myinvest.R
import com.example.myinvest.ativos.Ativo

class BuscaAdapter(private val dataBusca: MutableList<Ativo>, private val context: Context,
                   val onClick:(Ativo) -> Unit): RecyclerView.Adapter<BuscaAdapter.BuscarViewHolder>() {


    inner class BuscarViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textCodAtivo = itemView.findViewById<TextView>(R.id.text_busca_cod)
        val textNome = itemView.findViewById<TextView>(R.id.text_busca_nome_empresa)
        fun bind(buscaAtivo: Ativo){
            textCodAtivo.text = buscaAtivo.codAtivo
            textNome.text = buscaAtivo.nome
            with(itemView){
                setOnClickListener{
                    onClick.invoke(buscaAtivo)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuscarViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_busca_recycle, parent, false)
        return  BuscarViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuscarViewHolder, position: Int) {
        holder.bind(dataBusca[position])
    }

    override fun getItemCount() = dataBusca.size

    fun updateList(dataBusca: MutableList<Ativo>){
        this.dataBusca.clear()
        this.dataBusca.addAll(dataBusca)
        notifyDataSetChanged()
    }
}