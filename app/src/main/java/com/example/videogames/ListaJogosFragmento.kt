package com.example.videogames

import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.videogames.databinding.FragmentoListajogosFragmentoBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ListaJogosFragmento.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaJogosFragmento : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentoListajogosFragmentoBinding? = null
    private val binding get() = _binding!!

    var jogoSelecionado : Jogo? = null
        set(value) {
            field = value

            val mostrarEliminarAlterar = (value != null)

            val activity = activity as MainActivity
            activity.mostraOpcaoMenu(R.id.action_editar, mostrarEliminarAlterar)
            activity.mostraOpcaoMenu(R.id.action_eliminar, mostrarEliminarAlterar)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentoListajogosFragmentoBinding.inflate(inflater, container, false)
        return binding.root


    }

    private var adapterJogos: AdapterJogos? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterJogos= AdapterJogos(this)
        binding.recyclerViewJogos.adapter = adapterJogos
        binding.recyclerViewJogos.layoutManager = LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_JOGOS,null,this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_lista_jogos
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val ID_LOADER_JOGOS=0

    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {

        return CursorLoader(
            requireContext(),
            VideogamesContentProvider.ENDERECO_JOGOS,
            TabelaJogos.CAMPOS,
            null, null,
            TabelaJogos.CAMPO_NOME
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterJogos!!.cursor=data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (adapterJogos != null) {
            adapterJogos!!.cursor = null
        }
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean {
        return when (item.itemId) {
            R.id.action_adicionar -> {
                adicionaJogo()
                true
            }
            R.id.action_editar -> {
                editarJogo()
                true
            }
            R.id.action_eliminar -> {
                eliminarJogo()
                true
            }
            else -> false
        }
    }

    private fun eliminarJogo() {
        val acao = ListaJogosFragmentoDirections.actionListaJogosFragmentoToEliminarJogoFragmento(jogoSelecionado!!)
        findNavController().navigate(acao)
    }

    private fun editarJogo() {
        val acao = ListaJogosFragmentoDirections.actionListaJogosFragmentoToEditarJgoFragmento(jogoSelecionado!!)
        findNavController().navigate(acao)
    }

    private fun adicionaJogo() {
        val acao = ListaJogosFragmentoDirections.actionListaJogosFragmentToEditarJogoFragmento(null)
        findNavController().navigate(acao)
    }

}