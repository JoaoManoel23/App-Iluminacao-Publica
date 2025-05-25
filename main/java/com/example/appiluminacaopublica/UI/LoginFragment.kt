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

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etUsername = view.findViewById<EditText>(R.id.editTextTextEmailAddress)
        val etPassword = view.findViewById<EditText>(R.id.editTextTextPassword)
        val btnLogin = view.findViewById<Button>(R.id.button_login)
        val btnRegister = view.findViewById<Button>(R.id.button_register)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            val sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val savedUsername = sharedPref.getString("username", "")
            val savedPassword = sharedPref.getString("password", "")

            if (username == savedUsername && password == savedPassword) {
                Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Toast.makeText(context, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}