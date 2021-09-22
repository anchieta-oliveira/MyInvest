package com.example.myinvest


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myinvest.adapters.BuscaAdapter
import com.example.myinvest.application.MyInvestApplication
import com.example.myinvest.ativos.Ativo
import com.example.myinvest.ativos.ConectionApi
import com.example.myinvest.ativos.Operacao
import kotlinx.android.synthetic.main.add_ativo.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main


class AddAtivo : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_ativo)
        val recycleViewBsuca = findViewById<RecyclerView>(R.id.recycle_busca)
        recycleViewBsuca.visibility = View.GONE



        pesquisaAtivo()
        incluir()


    }

    fun incluir(){

        val editData = findViewById<EditText>(R.id.edit_data)
        val editPreco = findViewById<EditText>(R.id.edit_preco)
        val ediQuntidade = findViewById<EditText>(R.id.edit_qauntidade)
        val buttonIncluir = findViewById<Button>(R.id.button_incluir_ativo)
        val buttonTipo = findViewById<Switch>(R.id.button_conpra_venda)
        buttonIncluir.setOnClickListener{
            val codAtivo = intent.getStringExtra("AtivoBusca").toString()

            val tipo = if (buttonTipo.isChecked){ "VENDA" } else{"COMPRA"}

            val qunatidade = ediQuntidade.text.toString().toInt()
            val precoCompra = editPreco.text.toString().toDouble()
            val precoMedioInicial = ((precoCompra)*qunatidade)/qunatidade

            val novaOperacao = Operacao(codAtivo, tipo,
                    editData.text.toString(),qunatidade, precoCompra, null)

            val novoAtivo = Ativo(codAtivo,intent.getStringExtra("AtivoNome"), qunatidade, precoMedioInicial)


            CoroutineScope(Default).launch {
                try {
                    MyInvestApplication.instance.helperDBAtivos?.addAtivo(novoAtivo)
                    MyInvestApplication.instance.helperDBAtivos?.addOperacao(novaOperacao)
                }catch (e: Exception){}

                withContext(Main) {
                    val irHome = Intent(this@AddAtivo, MainActivity::class.java)
                    startActivity(irHome)

                }
            }
        }

    }
    fun pesquisaAtivo(){
        val recycleViewBsuca = findViewById<RecyclerView>(R.id.recycle_busca)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(busca: String?): Boolean {
                if (busca != null) {
                    try {
                        carregarRecycleBusca(busca)

                    }catch (e: Exception){}
                }else{
                    recycleViewBsuca.visibility = View.GONE
                }
                return false
            }
            override fun onQueryTextChange(busca: String?): Boolean {
                if (busca != null) {
                    try {
                        //carregarRecycleBusca(busca)

                    }catch (e: Exception){}
                }else{
                    recycleViewBsuca.visibility = View.GONE
                }
                return false
            }
        })
    }


    fun atualizarPerfilAtivo(){
        val textNomeAtivo = findViewById<TextView>(R.id.nome_ativo)
        val textCodAtivo = findViewById<TextView>(R.id.cod_ativo)
        textCodAtivo.text = intent.getStringExtra("AtivoBusca").toString()
        textNomeAtivo.text = intent.getStringExtra("AtivoNome").toString()
    }

    private fun carregarRecycleBusca(busca:String) {

        val buttonIncluir = findViewById<Button>(R.id.button_incluir_ativo)
        CoroutineScope(Default).launch {

            val listaBsuca = ConectionApi().pesquisaAtivo(busca)

            withContext(Main){
                val conteinerPesquisa = findViewById<LinearLayout>(R.id.conteiner_pesquisa)
                val recycleViewBsuca = findViewById<RecyclerView>(R.id.recycle_busca)
                val buscaAdapter = BuscaAdapter( listaBsuca, this@AddAtivo) { it ->
                    //eventos de Click
                    intent.putExtra("AtivoBusca", it.codAtivo)
                    intent.putExtra("AtivoNome", it.nome)
                    atualizarPerfilAtivo()
                    conteinerPesquisa.visibility = View.GONE
                    buttonIncluir.visibility = View.VISIBLE
                }
                try{
                    recycleViewBsuca.adapter = buscaAdapter
                    recycleViewBsuca.visibility = View.VISIBLE
                }catch (e:Exception){}

            }

        }

    }

}