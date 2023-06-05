package com.example.videogames

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class VideogamesContentProvider : ContentProvider() {

    private var bdOpenHelper : BDJogosOpenHelper?=null

    override fun onCreate(): Boolean {
        bdOpenHelper= BDJogosOpenHelper(context)

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = bdOpenHelper!!.readableDatabase
        val id =uri.lastPathSegment

        val endereco = uriMatcher().match(uri)


        val tabela = when (endereco){
            URI_JOGADORES, URI_JOGADOR_ID -> TabelaJogadores(db)
            URI_JOGOS, URI_JOGO_ID -> TabelaJogos(db)
            else -> null

        }

        var (selecao, argsSelecao) = when (endereco){
            URI_JOGADOR_ID, URI_JOGO_ID -> Pair("${BaseColumns._ID}=?", arrayOf(id))
            else -> Pair(selection,selectionArgs)
        }

        return tabela?.consulta(
            projection as Array<String>,
            selecao ,
            argsSelecao as Array<String>,
            null,null,
            sortOrder)
    }


    override fun getType(uri: Uri): String? {
        val endereco = uriMatcher().match(uri)

        return when(endereco){

            URI_JOGOS-> "vnd.android.cursor.dir/$JOGOS"
            URI_JOGADORES -> "vnd.android.cursor.dir/$JOGADORES"
            URI_JOGO_ID -> "vnd.android.cursor.item/$JOGOS"
            URI_JOGADOR_ID -> "vnd.android.cursor.item/$JOGADORES"
            else -> null

        }

    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = bdOpenHelper!!.writableDatabase

        val endereco = uriMatcher().match(uri)

        val tabela = when (endereco){
            URI_JOGADORES -> TabelaJogadores(db)
            URI_JOGOS -> TabelaJogos(db)
            else -> return null

        }

        val id = tabela.insere(values!!)
        if(id == -1L){
            return null
        }

        return Uri.withAppendedPath(uri,id.toString())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {


        val db = bdOpenHelper!!.writableDatabase

        val endereco = uriMatcher().match(uri)

        val tabela = when (endereco) {
            URI_JOGADOR_ID -> TabelaJogadores(db)
            URI_JOGO_ID -> TabelaJogos(db)
            else -> return 0

        }

        val id = uri.lastPathSegment!!

        return tabela.elimina( "${BaseColumns._ID}=?", arrayOf(id))
    }


    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?):
            Int {

        val db = bdOpenHelper!!.writableDatabase

        val endereco = uriMatcher().match(uri)

        val tabela = when (endereco){
            URI_JOGADOR_ID -> TabelaJogadores(db)
            URI_JOGO_ID -> TabelaJogos(db)
            else -> return 0

        }

        val id = uri.lastPathSegment!!

        return tabela.altera(values!!,"${BaseColumns._ID}=?", arrayOf(id))

    }

    companion object{

        private const val AUTORIDADE ="com.example.videogames"
        const val JOGOS = "jogos"
        const val JOGADORES="jogadores"
        private const val URI_JOGO_ID=200
        private const val URI_JOGADOR_ID =101
        private const val URI_JOGADORES=100
        private const val URI_JOGOS =201

        private val ENDERECO_BASE = Uri.parse("content://$AUTORIDADE")
        val ENDERECO_JOGADORES  = Uri.withAppendedPath(ENDERECO_BASE, JOGADORES)
        val ENDERECO_JOGOS  = Uri.withAppendedPath(ENDERECO_BASE, JOGOS)

        fun uriMatcher()=UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, JOGADORES, URI_JOGADORES)
            addURI(AUTORIDADE, "$JOGADORES/#", URI_JOGADOR_ID)
            addURI(AUTORIDADE, JOGOS, URI_JOGOS)
            addURI(AUTORIDADE, "$JOGOS/#", URI_JOGO_ID)
        }

    }


}