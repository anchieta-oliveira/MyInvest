package com.example.myinvest.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.myinvest.ativos.Ativo
import com.example.myinvest.ativos.Operacao

class HelperDBAtivos(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "AtivosDB.db"
    }

    private val CREATE_TABLE_ATIVOS =
        "CREATE TABLE ${AtivoContract.AtivoEntry.TABLE_NAME} (" +
                "${AtivoContract.AtivoEntry.COLUMN_NAME_CODIGO} TEXT PRIMARY KEY," +
                "${AtivoContract.AtivoEntry.COLUMN_NAME_NOME} TEXT)"

    private val CREATE_TABLE_OPERACAO =
        "CREATE TABLE ${AtivoContract.Opereraca.TABLE_NAME} (" +
                "${AtivoContract.Opereraca.COLUMN_NAME_CODIGO} TEXT REFERENCES ${AtivoContract.AtivoEntry.TABLE_NAME}" +
                "(${AtivoContract.AtivoEntry.COLUMN_NAME_CODIGO})," +
                "${AtivoContract.Opereraca.COLUMN_NAME_TIPO} TEXT," +
                "${AtivoContract.Opereraca.COLUMN_NAME_DATA} TEXT," +
                "${AtivoContract.Opereraca.COLUMN_NAME_QUANT} TEXT," +
                "${AtivoContract.Opereraca.COLUMN_NAME_PRECO} TEXT," +
                "${BaseColumns._ID} integer PRIMARY KEY AUTOINCREMENT)"


    private val DROP_TABLE_ATIVO = "DROP TABLE IF EXISTS ${AtivoContract.AtivoEntry.TABLE_NAME}"
    private val DROP_TABLE_OPERA = "DROP TABLE IF EXISTS ${AtivoContract.AtivoEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_ATIVOS)
        db?.execSQL(CREATE_TABLE_OPERACAO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL(DROP_TABLE_ATIVO)
            db?.execSQL(DROP_TABLE_OPERA)
        }
    }


    fun addAtivo(ativo: Ativo) {
        val db = writableDatabase ?: return
        var valores = ContentValues().apply {
            put(AtivoContract.AtivoEntry.COLUMN_NAME_CODIGO, ativo.codAtivo)
            put(AtivoContract.AtivoEntry.COLUMN_NAME_NOME, ativo.nome)
        }
        db.insert(AtivoContract.AtivoEntry.TABLE_NAME, null, valores)
    }


    fun getAtivo(): MutableList<Ativo> {
        val db = readableDatabase ?: return mutableListOf<Ativo>()
        val lista = mutableListOf<Ativo>()
        val cursor =
            db.query(AtivoContract.AtivoEntry.TABLE_NAME, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            try {
                val ativoBd = Ativo(
                    cursor.getString(cursor.getColumnIndexOrThrow(AtivoContract.AtivoEntry.COLUMN_NAME_CODIGO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(AtivoContract.AtivoEntry.COLUMN_NAME_NOME))
                )
                ativoBd.setInfoAtivo()

                lista.add(ativoBd)
            } catch (e: Exception) {
            }

        }
        db.close()
        return lista
    }

    fun addOperacao(opera: Operacao) {
        val db = writableDatabase ?: return
        var valores = ContentValues().apply {
            put(AtivoContract.Opereraca.COLUMN_NAME_CODIGO, opera.codAtivo)
            put(AtivoContract.Opereraca.COLUMN_NAME_TIPO, opera.tipo)
            put(AtivoContract.Opereraca.COLUMN_NAME_DATA, opera.data)
            put(AtivoContract.Opereraca.COLUMN_NAME_QUANT, opera.quantidade)
            put(AtivoContract.Opereraca.COLUMN_NAME_PRECO, opera.preco)
        }
        db.insert(AtivoContract.Opereraca.TABLE_NAME, null, valores)
    }

    fun getOpera(codFiltro: String): MutableList<Operacao> {
        val db = readableDatabase ?: return mutableListOf<Operacao>()
        var where = "${AtivoContract.Opereraca.COLUMN_NAME_CODIGO} LIKE ?"
        var args: Array<String> = arrayOf()
        args = arrayOf(codFiltro)
        Log.i("Atual ", "$codFiltro")
        var lista = mutableListOf<Operacao>()
        var cursor =
            db.query(AtivoContract.Opereraca.TABLE_NAME, null, where, args, null, null, null)
        while (cursor.moveToNext()) {
            var opera = Operacao(
                cursor.getString(cursor.getColumnIndexOrThrow(AtivoContract.Opereraca.COLUMN_NAME_CODIGO)),
                cursor.getString(cursor.getColumnIndexOrThrow(AtivoContract.Opereraca.COLUMN_NAME_TIPO)),
                cursor.getString(cursor.getColumnIndexOrThrow(AtivoContract.Opereraca.COLUMN_NAME_DATA)),
                cursor.getInt(cursor.getColumnIndexOrThrow(AtivoContract.Opereraca.COLUMN_NAME_QUANT)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(AtivoContract.Opereraca.COLUMN_NAME_PRECO)),
                cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            )
            lista.add(opera)
        }
        db.close()
        return lista
    }

    fun excluirOpera(opera: Operacao) {
        val db = writableDatabase ?: return
        val sql = "DELETE FROM ${AtivoContract.Opereraca.TABLE_NAME} WHERE ${BaseColumns._ID} = ?"
        val arg = arrayOf(opera.id)
        try {
            db.execSQL(sql, arg)
            db.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun excluirAtivo(ativo: String) {
        val db = writableDatabase ?: return
        val sql =
            "DELETE FROM ${AtivoContract.AtivoEntry.TABLE_NAME} WHERE ${AtivoContract.AtivoEntry.COLUMN_NAME_CODIGO} = ?"
        val arg = arrayOf(ativo)
        try {
            db.execSQL(sql, arg)
            db.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}