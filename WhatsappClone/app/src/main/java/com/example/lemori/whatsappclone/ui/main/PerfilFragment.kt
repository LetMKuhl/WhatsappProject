package com.example.lemori.whatsappclone.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lemori.whatsappclone.InicioActivity
import com.example.lemori.whatsappclone.R
import kotlinx.android.synthetic.main.fragment_perfil.*

class PerfilFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_perfil, container, false)

        carregaInfoUser(view)

        return view

    }

    private fun carregaInfoUser( view : View?) {

        view?.tvNome.text = (activity as InicioActivity).usuarioLogado.getString("nome")
        view?.tvEmail.text = (activity as InicioActivity).usuarioLogado.getString("email")

    }

    companion object {
        @JvmStatic
        fun newInstance() = PerfilFragment()
    }
}