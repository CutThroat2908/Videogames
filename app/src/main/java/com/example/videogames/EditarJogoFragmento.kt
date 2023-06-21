package com.example.videogames

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import android.database.Cursor
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import com.example.videogames.databinding.FragmentoEditarJogoBinding
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import java.util.*

private const val ID_LOADER_JOGADORES = 0

class EditarJogoFragmento : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var jogo: Jogo?= null
    private var _binding: FragmentoEditarJogoBinding? = null
    private var dataPub : Calendar? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentoEditarJogoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarViewDataPub.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            if (dataPub == null) dataPub = Calendar.getInstance()
            dataPub!!.set(year, month, dayOfMonth)
        }

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_JOGADORES, null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val jogo = EditarJogoFragmento.fromBundle(requireArguments()).livro

        if (jogo != null) {
            activity.atualizaNome(R.string.editar_jogo_label)
            binding.editTextNome.setText(jogo.titulo)
            binding.editTextIsbn.setText(jogo.isbn)
        } else {
            activity.atualizaNome(R.string.novo_jogo_label)
        }
        this.jogo = jogo
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaJogos()
                true
            }
            else -> false
        }
    }

    private fun voltaListaJogos() {
        findNavController().navigate(R.id.action_editarJogoFragmento_to_ListaJogosFragmento)
    }

    private fun guardar() {
        val nome = binding.editTextNome.text.toString()
        if (nome.isBlank()) {
            binding.editTextNome.error = getString(R.string.nome_obrigatorio)
            binding.editTextNome.requestFocus()
            return
        }

        val jogadorId = binding.spinnerJogadores.selectedItemId

        val genero = binding.editTextGenero.text.toString()
        if (nome.isBlank()) {
            binding.editTextGenero.error = getString(R.string.genero_obrigatorio)
            binding.editTextGenero.requestFocus()
            return
        }

        val isbn = binding.editTextIsbn.text.toString()

        if (jogo == null) {
            val jogo = Jogo(
                nome,
                jogador("?", jogadorId),
                isbn,
                genero)

            insereJogo(jogo)
        } else {
            val jogo = jogo!!
            jogo.nome = nome
            jogo.jogador = Jogador("?", jogadorId)
            jogo.isbn = isbn
            jogo.genero = genero

            alteraJogo(jogo)
        }
    }

    private fun alteraJogo(jogo: Jogo) {
        val enderecoJogo = Uri.withAppendedPath(VideogamesContentProvider.ENDERECO_JOGOS, jogo.id.toString())
        val jogosAlterados = requireActivity().contentResolver.update(enderecoJogo, jogo.toContentValues(), null, null)

        if (jogosAlterados == 1) {
            Toast.makeText(requireContext(), R.string.jogo_guardado_com_sucesso, Toast.LENGTH_LONG).show()
            voltaListaJogos()
        } else {
            binding.editTextNome.error = getString(R.string.erro_guardar_jogo)
        }
    }

    private fun insereJogo(
        jogo: Jogo
    ) {

        val id = requireActivity().contentResolver.insert(
            VideogamesContentProvider.ENDERECO_JOGOS,
            jogo.toContentValues()
        )

        if (id == null) {
            binding.editTextNome.error = getString(R.string.erro_guardar_jogo)
            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.jogo_guardado_com_sucesso),
            Toast.LENGTH_SHORT
        ).show()
        voltaListaJogos()
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            VideogamesContentProvider.ENDERECO_JOGADORES,
            TabelaJogadores.CAMPOS,
            null, null,
            TabelaJogadores.CAMPO_NOME
        )
    }
    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (_binding != null) {
            binding.spinnerJogadores.adapter = null
        }
    }
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data == null) {
            binding.spinnerJogadores.adapter = null
            return
        }

        binding.spinnerJogadores.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaJogadores.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )

        mostraCategoriaSelecionadaSpinner()
    }

    private fun mostraCategoriaSelecionadaSpinner() {
        if (jogo == null) return

        val idJogador = jogo!!.idJogadores

        val ultimoJogador = binding.spinnerJogadores.count - 1
        for (i in 0..ultimoJogador) {
            if (idJogador == binding.spinnerJogadores.getItemIdAtPosition(i)) {
                binding.spinnerJogadores.setSelection(i)
                return
            }
        }
    }
}
