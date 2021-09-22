package com.example.myinvest.ativos

data class Operacao(
    val codAtivo:String,
    val tipo:String,
    val data:String,
    val quantidade:Int,
    val preco:Double,
    val id:Int?
)