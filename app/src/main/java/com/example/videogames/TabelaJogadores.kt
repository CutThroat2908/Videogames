package com.example.videogames

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaJogadores(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA($CHAVE_TABELA,$CAMPO_NOME TEXT NOT NULL,$CAMPO_EMAIL TEXT NOT NULL,$CAMPO_TELEFONE INT NOT NULL,$CAMPO_IDADE INT NOT NULL,$CAMPO_CIDADE TEXT NOT NULL,$CAMPO_ESTADO TEXT NOT NULL,$CAMPO_PAIS TEXT NOT NULL)")
    }

    companion object{
        const val NOME_TABELA = "Jogadores"
        const val  CAMPO_NOME = "nome"
        const val  CAMPO_EMAIL = "email"
        const val  CAMPO_TELEFONE = "telefone"
        const val  CAMPO_IDADE = "idade"
        const val  CAMPO_CIDADE = "cidade"
        const val  CAMPO_ESTADO = "estado"
        const val  CAMPO_PAIS = "pais"

        val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_NOME, CAMPO_EMAIL, CAMPO_TELEFONE, CAMPO_IDADE, CAMPO_CIDADE, CAMPO_ESTADO, CAMPO_PAIS)
    }
}