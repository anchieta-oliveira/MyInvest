package com.example.myinvest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myinvest.R
import com.example.myinvest.ativos.Operacao

class OperaAdapter(private val dataOpera: MutableList<Operacao>, private val context:Context,
                   val onClick:(Operacao) -> Unit):RecyclerView.Adapter<OperaAdapter.OperaViewHolder>() {
    inner class OperaViewHolder(view: View):RecyclerView.ViewHolder(view){
        val itemData = itemView.findViewById<TextView>(R.id.titulo_tabela_data)
        val itemOpera = itemView.findViewById<TextView>(R.id.titulo_tabela_opera)
        val itemQtde = itemView.findViewById<TextView>(R.id.titulo_tabela_qtde)
        val itemPreco = itemView.findViewById<TextView>(R.id.preco)
        val bottonExclir = itemView.findViewById<ImageButton>(R.id.button_excluir_opera)

        fun bind(opera: Operacao){
            itemData.text = opera.data
            itemOpera.text = opera.tipo
            itemQtde.text = opera.quantidade.toString()
            itemPreco.text = opera.preco.toString()
            /*
            bottonExclir.setOnClickListener{
                if (dataOpera.size == 1) {
                    MyInvestApplication.instance.helperDBAtivos?.excluirAtivo(opera.codAtivo)
                }
                MyInvestApplication.instance.helperDBAtivos?.excluirOpera(opera)
                dataOpera.remove(opera)
                notifyItemRemoved(adapterPosition)
                VerAtivo().atualizaarPagina()
            }

             */
            with(bottonExclir){
                setOnClickListener{
                    onClick.invoke(opera)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycle_opera, parent, false)
        return OperaViewHolder(view)
    }

    override fun onBindViewHolder(holder: OperaViewHolder, position: Int) {
        holder.bind(dataOpera[position])
    }

    override fun getItemCount() = dataOpera.size
}