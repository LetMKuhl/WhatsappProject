package com.example.lemori.whatsappclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.lemori.whatsappclone.utils.Constanst
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_cadastro)

        this.auth = FirebaseAuth.getInstance()

        tbCadastro.setNavigationOnClickListener {
            abreMainActivity()
        }

        btnRegister.setOnClickListener() {
            cadastro()
        }

    }

    private fun cadastro() {

        if (validacaoCadastro()) {

            cadastroCarregando(true)

            auth.createUserWithEmailAndPassword(
                edtEmailLogin.text.toString(),
                edtPasswordRegister.text.toString()
            ).addOnCompleteListener { cadastro ->

                if (cadastro.isSuccessful) {

                    auth.currentUser!!.sendEmailVerification().addOnCompleteListener { envioEmail ->

                        if (cadastro.isSuccessful) {

                            val db = FirebaseFirestore.getInstance()

                            val usuario = hashMapOf<String, String>(
                                "foto" to Constanst.URL_DEFAULT_PROFILE_PHOTO,
                                "nome" to edtNameRegister.text.toString(),
                                "email" to auth.currentUser!!.email
                            )

                            db.collection("usuarios")
                                .document(auth.currentUser!!.uid)
                                .set(usuario)
                                .addOnSuccessListener {

                                    sucessoCadastro()

                                }.addOnFailureListener{

                                    auth.currentUser!!.delete().addOnCompleteListener{

                                        erroCadastro(envioEmail.exception?.message!!)
                                        cadastroCarregando(false)

                                    }

                                }

                            sucessoCadastro()

                        } else {

                            auth.currentUser!!.delete().addOnCompleteListener {

                                erroCadastro(envioEmail.exception?.message!!)

                                cadastroCarregando(false)

                            }

                        }

                    }

                } else {

                    erroCadastro("Falha ao realizar a operação. Motivo : ${cadastro.exception?.message}")

                    cadastroCarregando(false)

                }

            }

        } else {

            erroCadastro("Verifique os campos incorretos.")

        }

    }

    private fun sucessoCadastro() {

        abreMainActivity()

    }

    private fun cadastroCarregando(flag: Boolean) {

        edtNameRegister.isEnabled = !flag
        edtEmailLogin.isEnabled = !flag
        edtPasswordRegister.isEnabled = !flag

        btnRegister.isEnabled = !flag

        btnRegister.text = if (flag) getString(R.string.carregando) else "Cadastrar"

        pgCadastro.visibility = if (flag) View.VISIBLE else View.INVISIBLE

    }

    private fun erroCadastro(s: String) {

        Toast.makeText(this, s, Toast.LENGTH_LONG).show()

    }

    private fun validacaoCadastro(): Boolean {

        if (edtNameRegister.text.isBlank() || edtNameRegister.text.isEmpty()) {

            edtNameRegister.error = "Nome de usuário incorreto. Digite um nome valido."
            edtNameRegister.requestFocus()
            return false

        }



        if (edtEmailLogin.text.isBlank() || edtEmailLogin.text.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(
                edtEmailLogin.text
            ).matches()
        ) {

            edtEmailLogin.error = "Endereço de e-mail incorreto. Digite um e-mail valido."
            edtEmailLogin.requestFocus()
            return false

        }

        if (edtPasswordRegister.text.length < 8) {

            edtPasswordRegister.error = "Sua senha de acesso deve ter no mínimo 8 caracteres."
            edtPasswordRegister.requestFocus()
            return false

        } else if (edtPasswordRegister.text.isEmpty() || edtPasswordRegister.text.isBlank()) {

            edtPasswordRegister.error = "Digite uma senha valida"
            edtPasswordRegister.requestFocus()
            return false

        }

        return true

    }

    private fun abreMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

