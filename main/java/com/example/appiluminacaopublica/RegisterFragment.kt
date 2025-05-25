package com.example.appiluminacaopublica.UI

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appiluminacaopublica.R

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etUsername = view.findViewById<EditText>(R.id.etUsernameRegister)
        val etPassword = view.findViewById<EditText>(R.id.etPasswordRegister)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)
        val btnBackToLogin = view.findViewById<Button>(R.id.btnBackToLogin)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("username", username)
                    putString("password", password)
                    apply()
                }

                Toast.makeText(context, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        btnBackToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}