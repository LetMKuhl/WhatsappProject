package com.example.lemori.whatsappclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        btnCadastro.setOnClickListener {

            abreCadastroActivity()

        }

    }

    private fun abreCadastroActivity() {
        startActivity(Intent(this, CadastroActivity::class.java))
        finish()
    }
}