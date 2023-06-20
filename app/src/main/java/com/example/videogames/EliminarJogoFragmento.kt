package com.example.videogames

import android.os.Bundle
import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.videogames.databinding.FragmentoEliminarJogoBinding
import android.net.Uri
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class EliminarJogoFragmento : Fragment() {
    private lateinit var jogo: Jogo
    private var _binding: FragmentoEliminarJogoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentoEliminarJogoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        jogo = EliminarJogoFragmento.fromBundle(requireArguments()).livro

        binding.textViewNome.text = jogo.nome
        binding.textViewISBN.text = jogo.isbn
        binding.textViewGenero.text = jogo.jogador.genero
        binding.textViewDesenvolvedora.text = jogo.desenvolvedora
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                eliminar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaLivros()
                true
            }
            else -> false
        }
    }

    private fun voltaListaLivros() {
        findNavController().navigate(R.id.action_eliminarJogoFragmento_to_ListaJogosFragmento)
    }

    private fun eliminar() {
            val enderecoJogo = Uri.withAppendedPath(VideogamesContentProvider.ENDERECO_JOGOS, jogo.id.toString())
        val numJogosEliminados = requireActivity().contentResolver.delete(enderecoJogo, null, null)

        if (numJogosEliminados == 1) {
            Toast.makeText(requireContext(), getString(R.string.jogo_eliminado_com_sucesso), Toast.LENGTH_LONG).show()
            voltaListaLivros()
        } else {
            Snackbar.make(binding.textViewNome, getString(R.string.erro_eliminar_jogo), Snackbar.LENGTH_INDEFINITE)
        }
    }
}