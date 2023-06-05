package com.example.videogames

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

class AdapterJogos(val fragmento: ListaJogosFragmento) : RecyclerView.Adapter<AdapterJogos.ViewHolderJogos>() {
    var cursor: Cursor? = null
        set(value){
            field = value
            notifyDataSetChanged()
        }



    inner class ViewHolderJogos (contentor: View) : RecyclerView.ViewHolder(contentor){
        private val textViewNome = contentor.findViewById<TextView>(R.id.textViewNome)
        private val textViewGenero = contentor.findViewById<TextView>(R.id.textViewGenero)

        init {
            contentor.setOnClickListener{
                viewHolderSelecionado?.deseleciona()
                seleciona()

            }
        }

        internal var jogo: Jogo?=null

            set(value) {
                field=value
                textViewNome.text = jogo?.nome ?:""
                textViewGenero.text= jogo?.genero ?:""
            }


        fun seleciona(){
            viewHolderSelecionado=this
            itemView.setBackgroundResource(R.color.teal_200)
        }

        fun deseleciona(){

            itemView.setBackgroundResource(android.R.color.white)
        }
    }

    private var viewHolderSelecionado:ViewHolderJogos?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderJogos {

        return  ViewHolderJogos(fragmento.layoutInflater.inflate(R.layout.item_jogo, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolderJogos, position: Int) {
        cursor!!.moveToPosition(position)
        holder.jogo = Jogo.fromCursor(cursor!!)
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0

    }


}