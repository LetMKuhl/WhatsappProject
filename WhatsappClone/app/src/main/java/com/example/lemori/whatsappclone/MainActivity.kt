package com.example.lemori.whatsappclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

            if (!user.isEmailVerified) {

                abreVerifiqueEmailActivity()

            } else {

                abreInicioActivity()

            }

        }

        btnEntrar.setOnClickListener {

            if (validacaoLogin()) {

                loginCarregando(true)

                auth.signInWithEmailAndPassword(
                    edtEmailLogin.text.toString(),
                    edtPasswordLogin.text.toString()
                ).addOnCompleteListener { login ->

                    if (login.isSuccessful) {

                        abreInicioActivity()

                    }else{

                        Toast.makeText(this, "Autenticação falou. Por favor, verifique seu e-mail e senha.", Toast.LENGTH_LONG).show()

                    }

                }

            } else {

                Toast.makeText(this, "Verifique os campos incorretos", Toast.LENGTH_LONG).show()

            }

        }

        btnCadastro.setOnClickListener {

            abreCadastroActivity()

        }

    }

    private fun loginCarregando(flag: Boolean) {

        edtEmailLogin.isEnabled = !flag
        edtPasswordLogin.isEnabled = !flag

        btnCadastro.isEnabled = !flag
        btnEntrar.isEnabled = !flag

        btnEntrar.text = if(flag) "Carregando" else "Entrar"

        pgLogin.visibility = if (flag) View.VISIBLE else View.INVISIBLE

    }

    private fun validacaoLogin(): Boolean {

        if (edtEmailLogin.text.isBlank() || edtEmailLogin.text.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(
                edtEmailLogin.text
            ).matches()
        ) {

            edtEmailLogin.error = "Endereço de e-mail incorreto. Digite um e-mail valido."
            edtEmailLogin.requestFocus()
            return false

        }

        if (edtPasswordLogin.text.length < 8) {

            edtPasswordLogin.error = "Sua senha de acesso deve ter no mínimo 8 caracteres."
            edtPasswordLogin.requestFocus()
            return false

        } else if (edtPasswordLogin.text.isEmpty() || edtPasswordLogin.text.isBlank()) {

            edtPasswordLogin.error = "Digite uma senha valida"
            edtPasswordLogin.requestFocus()
            return false

        }

        return true

    }

    private fun abreInicioActivity() {
        startActivity(Intent(this, InicioActivity::class.java))
        finish()
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