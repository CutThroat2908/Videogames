package com.example.videogames

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Jogo(
    var nome:String,
    var genero:String,
    var desenvolvedora: String?,
    var plataforma:String,
    var preco:Int,
    var idJogadores:Long,
    var id:Long = -1) : Serializable{

    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaJogos.CAMPO_NOME,nome)
        valores.put(TabelaJogos.CAMPO_GENERO,genero)
        valores.put(TabelaJogos.CAMPO_DESENVOLVEDORA,desenvolvedora)
        valores.put(TabelaJogos.CAMPO_PLATAFORMA,plataforma)
        valores.put(TabelaJogos.CAMPO_PRECO,preco)
        valores.put(TabelaJogos.CAMPO_FK_JOGADORES,idJogadores)
        return valores

    }

    companion object{
        fun fromCursor(cursor: Cursor):Jogo{
            val posNome = cursor.getColumnIndex(TabelaJogos.CAMPO_NOME)
            val posGenero = cursor.getColumnIndex(TabelaJogos.CAMPO_GENERO)
            val posDesenvolvedora = cursor.getColumnIndex(TabelaJogos.CAMPO_DESENVOLVEDORA)
            val posPlataforma = cursor.getColumnIndex(TabelaJogos.CAMPO_PLATAFORMA)
            val posPreco = cursor.getColumnIndex(TabelaJogos.CAMPO_PRECO)
            val posJogadoresFK = cursor.getColumnIndex(TabelaJogos.CAMPO_FK_JOGADORES)
            val posId = cursor.getColumnIndex(BaseColumns._ID)

            val nome = cursor.getString(posNome)
            val genero = cursor.getString(posGenero)
            val desenvolvedora = cursor.getString(posDesenvolvedora)
            val plataforma = cursor.getString(posPlataforma)
            val preco = cursor.getInt(posPreco)
            val jogadoresId = cursor.getLong(posJogadoresFK)
            val id = cursor.getLong(posId)
            return Jogo(nome,genero,desenvolvedora,plataforma,preco,jogadoresId,id)
        }
    }

}