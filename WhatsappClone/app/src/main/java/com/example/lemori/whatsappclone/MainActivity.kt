package com.example.lemori.whatsappclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {

            if (!user.isEmailVerified){

                abreVerifiqueEmailActivity()

            } else{



            }

        }

        btnCadastro.setOnClickListener {

            abreCadastroActivity()

        }

    }

    private fun abreVerifiqueEmailActivity() {
        startActivity(Intent(this, VerifiqueEmailActivity::class.java))
        finish()
    }

    private fun abreCadastroActivity() {
        startActivity(Intent(this, CadastroActivity::class.java))
        finish()
    }
}