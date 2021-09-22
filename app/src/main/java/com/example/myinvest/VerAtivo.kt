package com.example.myinvest

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myinvest.adapters.OperaAdapter
import com.example.myinvest.application.MyInvestApplication
import com.example.myinvest.ativos.Ativo
import com.example.myinvest.ativos.Operacao
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat

class VerAtivo : AppCompatActivity() {

    private var ativoAtual: Ativo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_ativo)
        val recycleViewOpera = findViewById<RecyclerView>(R.id.recycle_opera)
        recycleViewOpera.visibility = View.VISIBLE
        try {
            getExtraAtivo()
            setText()
            carregaOpera()
            AddOpera()

        }catch (e:Exception){}

    }


    fun AddOpera(){

        val buttonAddOpera = findViewById<FloatingActionButton>(R.id.add_opera)
        buttonAddOpera.setOnClickListener {
            val irAddOpera = Intent(this, AddOpera::class.java)
            irAddOpera.putExtra(EXTRA_ATIVO, ativoAtual)
            startActivity(irAddOpera)
        }
    }

    fun clickExcluirOpera(opera: Operacao){
        Log.i("Erro", "Cliquei no butão x")
        MyInvestApplication.instance.helperDBAtivos?.excluirOpera(opera)
        CoroutineScope(Default).launch {
            ativoAtual?.setInfoAtivo()
            withContext(Main){
                try {
                    carregaOpera()
                    setText()
                    Log.i("Erro", "Cliquei no butão x2")
                }catch (e:Exception){}

            }
        }

    }
    private fun carregarRecycleOpera(operaSQL:MutableList<Operacao>) {

        val recycleViewOpera = findViewById<RecyclerView>(R.id.recycle_opera)
        val operaAdapter = OperaAdapter(operaSQL, this) { it ->
            clickExcluirOpera(it)
            if (operaSQL.size == 1){
                MyInvestApplication.instance.helperDBAtivos?.excluirAtivo(it.codAtivo)
                val irHome = Intent(this, MainActivity::class.java)
                startActivity(irHome)
            }
            Log.i("TAG", "Cliquei ")
        }
        recycleViewOpera.adapter = operaAdapter

        recycleViewOpera.visibility = View.VISIBLE

    }
    fun carregaOpera(){
        CoroutineScope(Default).launch {
            // executa paralelo à UI thread
            val bdOpera = carregarDBOpera()

            withContext(Main) {
                // executa na UI thread
                carregarRecycleOpera(bdOpera)
            }
        }
    }


    fun carregarDBOpera(): MutableList<Operacao> {
       // val ativoAtual = intent.getStringExtra("VerAtivo")

        val operaSQL = MyInvestApplication.instance.helperDBAtivos?.getOpera(ativoAtual!!.codAtivo) ?: mutableListOf()
        return operaSQL
    }

    companion object{
        const val EXTRA_ATIVO: String = "EXTRA_ATIVO"
    }

    fun getExtraAtivo(){
        ativoAtual = intent.getParcelableExtra(EXTRA_ATIVO)

    }

    fun setText(){

        val tituloAtivo = findViewById<TextView>(R.id.cod_ativo)
        val nomeAtivo = findViewById<TextView>(R.id.nome_ativo)
        val posicaoAtual = findViewById<TextView>(R.id.posicao_atual)
        val custoTotal = findViewById<TextView>(R.id.custo_total)
        val variacaoAtivo = findViewById<TextView>(R.id.variacao_ativo)
        val resultado = findViewById<TextView>(R.id.text_resultado)
        val forDecimal = DecimalFormat("#,###.00")

        nomeAtivo.text = ativoAtual?.nome
        tituloAtivo.text = ativoAtual?.codAtivo
        posicaoAtual.text = forDecimal.format(ativoAtual?.posicaoAtual)
        custoTotal.text = forDecimal.format(ativoAtual?.custoTotal)
        variacaoAtivo.text = forDecimal.format(ativoAtual?.rentabilidae) + "%"
        resultado.text = forDecimal.format(ativoAtual?.resultado)

        if (ativoAtual?.rentabilidae!! > 0 ){
            variacaoAtivo.setTextColor(Color.GREEN)
            resultado.setTextColor(Color.GREEN)
        }else{
            variacaoAtivo.setTextColor(Color.RED)
            resultado.setTextColor(Color.RED)
        }
    }


}