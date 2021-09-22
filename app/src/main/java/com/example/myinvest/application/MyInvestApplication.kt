package com.example.myinvest.application

import android.app.Application
import com.example.myinvest.helpers.HelperDBAtivos

class MyInvestApplication: Application() {
    var helperDBAtivos:HelperDBAtivos? = null
        private set

    companion object{
        lateinit var instance:MyInvestApplication
    }

    override fun onCreate(){
        super.onCreate()
        instance = this
        helperDBAtivos = HelperDBAtivos(this)
    }
}