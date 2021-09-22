package com.example.myinvest

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myinvest.VerAtivo.Companion.EXTRA_ATIVO
import com.example.myinvest.adapters.AtivoAdapter
import com.example.myinvest.application.MyInvestApplication
import com.example.myinvest.ativos.Ativo
import com.example.myinvest.carteira.Carteira
import com.example.myinvest.graficos.GraficoPizza
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recycleViewAtivos = findViewById<RecyclerView>(R.id.recycleAtivos)

        recycleViewAtivos.visibility = View.GONE
        telaAbertura()
        buttonMenu()
        iniciarRecycleAtivo()
        expandirListaAtivos()


    }

    fun graficoCarteira(data:MutableList<Ativo>){
        val graficoCarteira = findViewById<AAChartView>(R.id.aa_chart_view)
        graficoCarteira.aa_drawChartWithChartModel(GraficoPizza().graficoPizzaAtivo(data))



    }

    private fun buttonMenu() {
        val buttonMenu = findViewById<FloatingActionButton>(R.id.menu_button)
        buttonMenu.setOnClickListener {
            val intent = Intent(this, AddAtivo::class.java)
            startActivity(intent)

        }
    }

    private fun carregarRecycleAtivos(listaAtivos:MutableList<Ativo>){
        val recycleViewAtivos = findViewById<RecyclerView>(R.id.recycleAtivos)
        val ativoAdapter = AtivoAdapter(mutableListOf(), this) { it ->
                //eventos de Click
            val intent = Intent(this, VerAtivo::class.java)
            intent.putExtra(EXTRA_ATIVO, it)
            startActivity(intent)
        }
        ativoAdapter.updateList(listaAtivos)
        recycleViewAtivos.adapter = ativoAdapter
    }

    fun carregarBD(): MutableList<Ativo> {
        val ativosSQL = MyInvestApplication.instance.helperDBAtivos?.getAtivo() ?: mutableListOf()
        return ativosSQL
    }

    fun iniciarRecycleAtivo(){
        CoroutineScope(Default).launch {
            // executa parallel à UI thread

            val bdAtivos = carregarBD()

            withContext(Main) {
                // executa na UI thread
                val recycleViewAtivos = findViewById<RecyclerView>(R.id.recycleAtivos)
                try {
                    infoCarteira(bdAtivos)
                    carregarRecycleAtivos(bdAtivos)
                    recycleViewAtivos.visibility = View.VISIBLE
                    telaAberturaFechar()
                    graficoCarteira(bdAtivos)

                }catch (e:Exception){}

            }
        }
    }

    fun infoCarteira(data: MutableList<Ativo>){
        val forDecimal = DecimalFormat("#,###.00")
        val rentabilidadeCarteira = Carteira().getRentabilidade(data)
        val resultadoCarteira = Carteira().getresultadoCarteira(data)
        val posicaoCarteira = Carteira().getposicaoAtual(data)
        val textPosicaoCarteira = findViewById<TextView>(R.id.carteira_posicao)
        val textRentabilidadeCarteira = findViewById<TextView>(R.id.carteira_rentabilidade)

        textPosicaoCarteira.text = "R$ " + forDecimal.format(posicaoCarteira)
        textRentabilidadeCarteira.text = "R$ ${forDecimal.format(resultadoCarteira)} (${forDecimal.format(rentabilidadeCarteira)}%)"
        if (rentabilidadeCarteira <0){
            textRentabilidadeCarteira.setTextColor(Color.RED)
        }else{
            textRentabilidadeCarteira.setTextColor(Color.GREEN)
        }
    }

    fun expandirListaAtivos(){
        val buttonExmpandir = findViewById<ImageButton>(R.id.buton_expand_list)
        val graficoCarteira = findViewById<AAChartView>(R.id.aa_chart_view)

        buttonExmpandir.setOnClickListener {
            if (buttonExmpandir.rotation !=180f){
                fade(graficoCarteira)
                buttonExmpandir.apply {
                    animate().rotation(180f)
                }

            }else{
                aparecer(graficoCarteira)
                buttonExmpandir.apply {
                    animate().rotation(0f)
                }
            }

        }
    }

    fun telaAbertura(){
        val conteinerAbertura = findViewById<RelativeLayout>(R.id.tela_abertura)
        val nomeMyInvest = findViewById<ImageView>(R.id.text_icon)
        conteinerAbertura.visibility = View.VISIBLE
        nomeMyInvest.apply {
            scaleX = 0.6f
            scaleY = 0.6f
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f)
                .setDuration(200)
                .setListener(null)

            animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(1000)

        }

    }

    fun telaAberturaFechar(){
        val conteinerAbertura = findViewById<RelativeLayout>(R.id.tela_abertura)
        conteinerAbertura.apply {
            alpha = 1f
            visibility = View.VISIBLE
            animate().alpha(0f)
                .setDuration(100)
                .setListener(null)

        }

    }


    fun fade(view: View){
        var shortAnimationDuration: Int = 0
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        view.animate().alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE}})
    }

    fun aparecer(view: View){
        var shortAnimationDuration: Int = 0
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        view.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }
    }

    fun crossFade(viewOn: View, viewOf: View){
        fade(viewOf)
        aparecer(viewOn)
    }


}