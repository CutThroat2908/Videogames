package com.example.videogames

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Jogador (
    var nome:String,
    var email:String,
    val telefone:Int,
    val idade:Int,
    val cidade:String,
    val estado:String,
    val pais:String,
    var id:Long = -1
){

    fun toContentValues():ContentValues{
        val valores = ContentValues()

        valores.put(TabelaJogadores.CAMPO_NOME,nome)
        valores.put(TabelaJogadores.CAMPO_EMAIL,email)
        valores.put(TabelaJogadores.CAMPO_TELEFONE,telefone)
        valores.put(TabelaJogadores.CAMPO_IDADE,idade)
        valores.put(TabelaJogadores.CAMPO_CIDADE,cidade)
        valores.put(TabelaJogadores.CAMPO_ESTADO,estado)
        valores.put(TabelaJogadores.CAMPO_PAIS,pais)
        return valores

    }

    companion object{
        fun fromCursor(cursor:Cursor):Jogador{
            val posNome = cursor.getColumnIndex(TabelaJogadores.CAMPO_NOME)
            val posEmail = cursor.getColumnIndex(TabelaJogadores.CAMPO_EMAIL)
            val posTelefone = cursor.getColumnIndex(TabelaJogadores.CAMPO_TELEFONE)
            val posIdade = cursor.getColumnIndex(TabelaJogadores.CAMPO_IDADE)
            val posCidade = cursor.getColumnIndex(TabelaJogadores.CAMPO_CIDADE)
            val posEstado = cursor.getColumnIndex(TabelaJogadores.CAMPO_ESTADO)
            val posPais = cursor.getColumnIndex(TabelaJogadores.CAMPO_PAIS)
            val posId = cursor.getColumnIndex(BaseColumns._ID)

            val nome = cursor.getString(posNome)
            val email = cursor.getString(posEmail)
            val telefone = cursor.getInt(posTelefone)
            val idade = cursor.getInt(posIdade)
            val cidade = cursor.getString(posCidade)
            val estado = cursor.getString(posEstado)
            val pais = cursor.getString(posPais)
            val id = cursor.getLong(posId)
            return Jogador(nome,email,telefone,idade,cidade,estado,pais,id)
        }
    }
}