package com.example.myinvest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myinvest.VerAtivo.Companion.EXTRA_ATIVO
import com.example.myinvest.application.MyInvestApplication
import com.example.myinvest.ativos.Ativo
import com.example.myinvest.ativos.Operacao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddOpera : AppCompatActivity() {


    private var ativoAtual: Ativo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_ativo)
        val conteinerPesquisa = findViewById<LinearLayout>(R.id.conteiner_pesquisa)
        val buttonIncluir = findViewById<Button>(R.id.button_incluir_ativo)
        conteinerPesquisa.visibility = View.GONE
        buttonIncluir.visibility = View.VISIBLE
        getExtraAtivo()
        try{

            atualizarPerfilAtivo()
            addAtivo()

        }catch (e:Exception){}

    }

    fun getExtraAtivo(){
        ativoAtual = intent.getParcelableExtra(EXTRA_ATIVO)

    }

    fun atualizarPerfilAtivo(){

        val textNomeAtivo = findViewById<TextView>(R.id.nome_ativo)
        val textCodAtivo = findViewById<TextView>(R.id.cod_ativo)
        textCodAtivo.text = ativoAtual?.codAtivo.toString()
        textNomeAtivo.text = ativoAtual?.nome.toString()

    }


    fun addAtivo(){
        val codAtivo = ativoAtual!!.codAtivo
        val nomeAtivo = ativoAtual?.codAtivo.toString()
        Log.i("Ativo novo", codAtivo)

        val editData = findViewById<EditText>(R.id.edit_data)
        val editPreco = findViewById<EditText>(R.id.edit_preco)
        val ediQuntidade = findViewById<EditText>(R.id.edit_qauntidade)
        val buttonIncluir = findViewById<Button>(R.id.button_incluir_ativo)
        val buttonTipo = findViewById<Switch>(R.id.button_conpra_venda)


        buttonIncluir.setOnClickListener{
            val tipo = if (buttonTipo.isChecked){ "VENDA" } else {"COMPRA"}

            val qunatidade = ediQuntidade.text.toString().toInt()
            val precoCompra = editPreco.text.toString().toDouble()


            val novaOperacao = Operacao(codAtivo, tipo,
                    editData.text.toString(),qunatidade, precoCompra, null)


            CoroutineScope(Default).launch {
                val ativoAtualizado = Ativo(codAtivo, nomeAtivo)
                try {
                    MyInvestApplication.instance.helperDBAtivos?.addOperacao(novaOperacao)
                    ativoAtualizado.setInfoAtivo()
                }catch (e: Exception){}

                withContext(Main) {
                    val irVerAtivo = Intent(this@AddOpera, VerAtivo::class.java)
                    irVerAtivo.putExtra(EXTRA_ATIVO, ativoAtualizado)
                    startActivity(irVerAtivo)
                }
            }
        }
    }
}
