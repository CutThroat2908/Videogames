package com.example.videogames

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.videogames.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var menu: Menu
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var idMenuAtual : Int = R.menu.menu_main
        set(value) {
            if (value != field) {
                field = value
                invalidateOptionsMenu()
            }
        }

    var fragment : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item.itemId == R.id.action_settings) {
            return true
        }
        val opcaoProcessada = when (fragment) {
            is ListaJogosFragmento -> (fragment as ListaJogosFragmento).processaOpcaoMenu(item)
            is EditarJogoFragmento -> (fragment as EditarJogoFragmento).processaOpcaoMenu(item)
            is EliminarJogoFragmento -> (fragment as EliminarJogoFragmento).processaOpcaoMenu(item)
            else -> false
        }

        return if (opcaoProcessada) { true } else { super.onOptionsItemSelected(item) }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    fun mostraOpcaoMenu(idOpcao: Int, mostrar: Boolean) {
        menu.findItem(idOpcao).setVisible(mostrar)
    }
    fun atualizaNome(label: Int) = binding.toolbar.setTitle(label)
}