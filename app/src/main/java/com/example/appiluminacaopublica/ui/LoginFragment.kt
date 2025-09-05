package com.example.appiluminacaopublica.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appiluminacaopublica.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.grpc.Context

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        
        val etUseremail = view.findViewById<EditText>(R.id.editTextTextEmailAddress)
        val etPassword = view.findViewById<EditText>(R.id.editTextTextPassword)
        val btnLogin = view.findViewById<Button>(R.id.button_login)
        val btnRegister = view.findViewById<Button>(R.id.button_register_login)
        val checkBoxRemember : CheckBox

        btnLogin.setOnClickListener {
//            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            val useremail = etUseremail.text.toString()
            val password = etPassword.text.toString()

            if (useremail.isBlank() || password.isBlank()) {
                Toast.makeText(context, "Preencha e-mail e senha!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(useremail,password).
                addOnCompleteListener{task->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(context, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
                    }
                }
//                    if (username == savedUsername && password == savedPassword) {
//                       Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
//                       findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                  } else {
//                      Toast.makeText(context, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
//                  }
        }
               btnRegister.setOnClickListener {
                  findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
               }

        val textEsqueceuSenha = view.findViewById<TextView>(R.id.textEsqueceuSenha)
        val edtEmail = view.findViewById<EditText>(R.id.editTextTextEmailAddress) // já deve existir no seu layout

        textEsqueceuSenha.setOnClickListener {
            val email = edtEmail.text.toString().trim()

            if (email.isBlank()) {
                Toast.makeText(context, "Digite seu e-mail para recuperar a senha", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    Toast.makeText(context, "E-mail de redefinição enviado!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Erro ao enviar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

    }

    companion object {
    }
}