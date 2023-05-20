package com.example.videogames



import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BDInstrumentedTest {

    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun apagaBD(){
        getAppContext().deleteDatabase(BDJogosOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consequeAbrirBD() {
        val db = getWritableDatabase()
        assert(db.isOpen)

    }

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BDJogosOpenHelper(getAppContext())
        val db = openHelper.writableDatabase
        return db
    }

    private fun insereJogador(db: SQLiteDatabase,jogador: Jogador) {

        jogador.id= TabelaJogadores(db).insere(jogador.toContentValues())
        assertNotEquals(-1,jogador.id)
    }
    private fun insereJogos( db: SQLiteDatabase,jogo: Jogo) {

        jogo.id = TabelaJogos(db).insere((jogo.toContentValues()))
        assertNotEquals(-1, jogo.id)
    }

    @Test
    fun consegueInserirJogadores(){
        val db = getWritableDatabase()

        val jogador = Jogador("João Maria","jmaria@meuemail.com",969696969,24,"Nova York","New York", "USA")
        val id= TabelaJogadores(db).insere((jogador.toContentValues()))
        TabelaJogadores(db).insere(jogador.toContentValues())
        assertNotEquals(-1,id)
    }

    @Test
    fun consegueInserirJogos(){
        val db = getWritableDatabase()
        val jogador = Jogador("João Maria","jmaria@meuemail.com",969696969,24,"Nova York","New York", "USA")
        insereJogador(db, jogador)

        val jogo1 = Jogo("The Elder Scrolls V: Skyrim","RPG","Bethesda Softworks","ALL",20,jogador.id)
        insereJogos(db, jogo1)

        val jogo2 = Jogo("Minecraft","Sandbox","Mojang","PC", 30,jogador.id)
        insereJogos(db, jogo2)
    }

    @Test
    fun consegueLerJogadores(){
        val db = getWritableDatabase()

        val jogador1 = Jogador("João Maria","jmaria@meuemail.com",969696969,24,"Nova York","New York", "USA")
        insereJogador(db, jogador1)

        val jogador2 = Jogador("Maria João","mjoao@meuemail.com",969696966,42,"Nova York","New York", "USA")
        insereJogador(db, jogador2)

        val tabelaJogadores = TabelaJogadores(db)
        val cursor = tabelaJogadores.consulta(
            TabelaJogadores.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(jogador1.id.toString()),
            null,
            null,
            null
        )
        assert(cursor.moveToNext())

        val jogadorBD = Jogador.fromCursor(cursor)
        assertEquals(jogador1,jogadorBD)

        val cursorTodosJogadores=tabelaJogadores.consulta(
            TabelaJogadores.CAMPOS,
            null,null,null,null,
            TabelaJogadores.CAMPO_NOME,

            )

        assert(cursorTodosJogadores.count>1)
    }

    @Test

    fun consegueLerJogos(){

        val db = getWritableDatabase()
        val jogador = Jogador("João Maria","jmaria@meuemail.com",969696969,24,"Nova York","New York", "USA")
        insereJogador(db, jogador)

        val jogo1 = Jogo("The Elder Scrolls V: Skyrim","RPG","Bethesda Softworks","ALL",20,jogador.id)
        insereJogos(db, jogo1)

        val jogo2 = Jogo("Minecraft","Sandbox","Mojang","PC", 30,jogador.id)
        insereJogos(db, jogo2)

        val tabelaJogos = TabelaJogos(db)
        val cursor = tabelaJogos.consulta(
            TabelaJogos.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(jogo1.id.toString()),
            null,
            null,
            null
        )
        assert(cursor.moveToNext())

        val JogoBD = Jogo.fromCursor(cursor)
        assertEquals(jogo1,JogoBD)

        val cursorTodosJogos=tabelaJogos.consulta(
            TabelaJogos.CAMPOS,
            null,null,null,null,
            TabelaJogos.CAMPO_NOME,

            )

        assert(cursorTodosJogos.count>1)


    }

    @Test
    fun consegueAlterarJogadores(){

        val db = getWritableDatabase()

        val jogador = Jogador("Rúben Andrade", "...", 999666999,21,"Portimão","Faro", "PT")
        insereJogador(db, jogador)

        jogador.email="randrade@gandasocio.com"
        val registosAlterados = TabelaJogadores(db).altera(jogador.toContentValues(),"${BaseColumns._ID}=?",
            arrayOf(jogador.id.toString())
        )

        assertEquals(1,registosAlterados)
    }

    @Test
    fun consegueAlterarJogos(){

        val db = getWritableDatabase()

        val jogador1 = Jogador("João Maria","jmaria@meuemail.com",969696969,24,"Nova York","New York", "USA")
        insereJogador(db, jogador1)

        val jogador2 = Jogador("Maria João","mjoao@meuemail.com",969696966,42,"Nova York","New York", "USA")
        insereJogador(db, jogador2)

        val jogo = Jogo("God of War","Adventure","...","PS",20, jogador2.id)
        insereJogos(db, jogo)

        jogo.idJogadores=jogador1.id
        jogo.desenvolvedora = "Santa Monica"

        val registosAlterados = TabelaJogos(db).altera(jogo.toContentValues(),"${BaseColumns._ID}=?",
            arrayOf(jogo.id.toString())
        )

        assertEquals(1,registosAlterados)
    }

    @Test
    fun consegueDeletarJogadores() {

        val db = getWritableDatabase()

        val jogador = Jogador("João Maria","jmaria@meuemail.com",969696969,24,"Nova York","New York", "USA")
        insereJogador(db, jogador)

        val registosEliminados = TabelaJogadores(db).elimina("${BaseColumns._ID}=?",
            arrayOf(jogador.id.toString())
        )

        assertEquals(1,registosEliminados)


    }

    @Test
    fun consegueDeletarJogos(){

        val db = getWritableDatabase()

        val jogador = Jogador("João Maria","jmaria@meuemail.com",969696969,24,"Nova York","New York", "USA")
        insereJogador(db, jogador)

        val jogo = Jogo("Minecraft","Sandbox","Mojang","PC", 30,jogador.id)
        insereJogos(db, jogo)


        val registosEliminados = TabelaJogos(db).elimina("${BaseColumns._ID}=?",
            arrayOf(jogo.id.toString())
        )

        assertEquals(1,registosEliminados)
    }


}