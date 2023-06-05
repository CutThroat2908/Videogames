package com.example.videogames

import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.videogames.databinding.FragmentoListajogosFragmentoBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ListaInstrumentosFragmento.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaJogosFragmento : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentoListajogosFragmentoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
        adapterJogos!!.cursor=null
    }


}