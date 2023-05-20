package com.example.videogames

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class InstrumentosContentProvider : ContentProvider() {

    private var bdOpenHelper : BDJogosOpenHelper?=null

    override fun onCreate(): Boolean {
        bdOpenHelper= BDJogosOpenHelper(context)

        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }



    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object{

        private const val AUTORIDADE ="com.example.videogames"
        const val JOGOS = "jogos"
        const val JOGADORES = "jogadores"
        const val URI_JOGOS = 200
        const val URI_JOGADORES = 100

        fun uriMatcher()=UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, JOGADORES, URI_JOGADORES)
            addURI(AUTORIDADE, JOGOS, URI_JOGOS)
        }
    }


}