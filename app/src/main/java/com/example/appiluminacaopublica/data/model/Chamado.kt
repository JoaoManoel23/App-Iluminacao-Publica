package com.example.appiluminacaopublica.data.model

import com.google.firebase.Timestamp

data class Chamado(
    val nome: String = "",
    val telefone: String = "",
    val email: String = "",
    val descricao: String = "",
    val dataHora: String= "",
    val localizacao: String = "",
    val latitude: String? = null,
    val longitude: String? = null
//    val imagemUri: String? = null
)
