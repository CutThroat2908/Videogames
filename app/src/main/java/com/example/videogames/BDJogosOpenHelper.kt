package com.example.videogames

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class BDJogosOpenHelper (
    context: Context?,
) : SQLiteOpenHelper(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS) {

    companion object{
        const val NOME_BASE_DADOS = "Jogos.db"
        private const val VERSAO_BASE_DADOS = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        requireNotNull(db)
        TabelaJogadores(db!!).cria()
        TabelaJogos(db!!).cria()

    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }





}