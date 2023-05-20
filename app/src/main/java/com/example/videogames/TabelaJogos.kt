package com.example.videogames

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaJogos(db: SQLiteDatabase):TabelaBD(db, NOME_TABELA) {


    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA($CHAVE_TABELA,$CAMPO_NOME TEXT NOT NULL, $CAMPO_GENERO TEXT NOT NULL,$CAMPO_DESENVOLVEDORA TEXT NOT NULL,$CAMPO_PLATAFORMA TEXT NOT NULL,$CAMPO_PRECO INTEGER NOT NULL,$CAMPO_FK_JOGADORES INTEGER NOT NULL," +
                " FOREIGN KEY($CAMPO_FK_JOGADORES) REFERENCES ${TabelaJogadores.NOME_TABELA}(${BaseColumns._ID})ON DELETE RESTRICT)")
    }

    companion object{
        const val NOME_TABELA = "Jogos"
        const val CAMPO_NOME = "nome"
        const val CAMPO_GENERO = "genero"
        const val CAMPO_DESENVOLVEDORA = "desenvolvedora"
        const val CAMPO_PLATAFORMA = "plataforma"
        const val CAMPO_PRECO = "preco"
        const val CAMPO_FK_JOGADORES = "id_Jogadores"

        val CAMPOS = arrayOf(BaseColumns._ID,
            TabelaJogos.CAMPO_NOME,
            TabelaJogos.CAMPO_GENERO,
            TabelaJogos.CAMPO_DESENVOLVEDORA,
            TabelaJogos.CAMPO_PLATAFORMA,
            TabelaJogos.CAMPO_PRECO,
            TabelaJogos.CAMPO_FK_JOGADORES
        )
    }
}