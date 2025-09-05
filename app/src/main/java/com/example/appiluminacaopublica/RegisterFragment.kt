package com.example.appiluminacaopublica

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.appiluminacaopublica.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val edtNome = view.findViewById<EditText>(R.id.edtNome)
        val edtCidade = view.findViewById<EditText>(R.id.edtCidade)
        val edtEmail = view.findViewById<EditText>(R.id.edtEmail)
        val edtSenha = view.findViewById<EditText>(R.id.edtSenha)
        val edtRua = view.findViewById<EditText>(R.id.edtRua)
        val edtBairro = view.findViewById<EditText>(R.id.edtBairro)
        val edtTelefone = view.findViewById<EditText>(R.id.edtTelefone)
        val btnRegister = view.findViewById<Button>(R.id.button_register)


        btnRegister.setOnClickListener {

            val nome = edtNome.text.toString()
            val cidade = edtCidade.text.toString()
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()
            val rua = edtRua.text.toString()
            val bairro = edtBairro.text.toString()
            val telefone = edtTelefone.text.toString()
            
            if(cidade.isBlank() || email.isBlank() || senha.isBlank() || rua.isBlank() || bairro.isBlank() || telefone.isBlank()){
                Toast.makeText(context,"Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email,senha)
                .addOnSuccessListener {
                    val uid = auth.currentUser?.uid?: return@addOnSuccessListener

                    val usuario = hashMapOf(
                        "nome" to nome,
                        "cidade" to cidade,
                        "email" to email,
                        "rua" to rua,
                        "bairro" to bairro,
                        "telefone" to telefone
                    )
                    db.collection("usuarios").document(uid).set(usuario)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registerFragment_to_feedFragment)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Erro ao salvar dados: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } .addOnFailureListener { e ->
                    Toast.makeText(context, "Erro no cadastro: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

//        val btnRegister = view.findViewById<Button>(R.id.button_register)
//
//        btnRegister.setOnClickListener {
//            findNavController().navigate(R.id.action_registerFragment_to_feedFragment)
//        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

    companion object {
    }
}