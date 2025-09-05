package com.example.appiluminacaopublica.ui.Feed.Chamados

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.appiluminacaopublica.R
//import com.example.appiluminacaopublica.data.appData
import com.example.appiluminacaopublica.data.model.Chamado
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
//import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ChamadosFragment : Fragment() {

    private lateinit var edtNome: EditText
    private lateinit var edtTelefone: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtDescricao: EditText
    private lateinit var txtDataHora: TextView
    private lateinit var txtLocalizacao: TextView
//    private lateinit var btnAdicionarImagem: Button
    private lateinit var btnConcluir: Button
//    private lateinit var imageSelecionada: ImageView

    private var imagemUri: Uri? = null
    private var latitude: String = ""
    private var longitude: String = ""

    // Código de permissão para localização
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    private val db = Firebase.firestore

    // Novo launcher para abrir a galeria (substitui startActivityForResult)
//    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        if (uri != null) {
//            imagemUri = uri
//            imageSelecionada.setImageURI(uri)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chamados, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtNome = view.findViewById(R.id.edtNome)
        edtTelefone = view.findViewById(R.id.edtTelefone)
        edtEmail = view.findViewById(R.id.edtEmail)
        edtDescricao = view.findViewById(R.id.edtDescricao)
        txtDataHora = view.findViewById(R.id.txtDataHora)
        txtLocalizacao = view.findViewById(R.id.txtLocalizacao)
//        btnAdicionarImagem = view.findViewById(R.id.btnAdicionarImagem)
        btnConcluir = view.findViewById(R.id.btnConcluir)
//        imageSelecionada = view.findViewById(R.id.imageSelecionada)

        val dataHoraAtual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        txtDataHora.text = "Data e Hora: $dataHoraAtual"

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        edtNome.setText(document.getString("nome") ?: "")
                        edtTelefone.setText(document.getString("telefone") ?: "")
                        edtEmail.setText(document.getString("email") ?: "")
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "Erro ao carregar dados do usuário",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
        obterLocalizacao()

//        btnAdicionarImagem.setOnClickListener {
//            abrirGaleria()
//        }

        btnConcluir.setOnClickListener {
            salvarChamado(dataHoraAtual,latitude,longitude)
        }
    }

    private fun obterLocalizacao() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                latitude = location.latitude.toString()
                longitude = location.longitude.toString()
                txtLocalizacao.text = "Localization: $latitude , $longitude"
            } else {
                txtLocalizacao.text = "Localização: Não disponível"
            }
        }
    }

//    private fun abrirGaleria() {
//        pickImageLauncher.launch("image/*")
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obterLocalizacao()
            } else {
                Toast.makeText(requireContext(), "Permissão de localização negada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun salvarChamado(dataHora: String,latitude: String, longitude: String) {
        val nome = edtNome.text.toString()
        val telefone = edtTelefone.text.toString()
        val email = edtEmail.text.toString()
        val descricao = edtDescricao.text.toString()

        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty() || descricao.isEmpty()) {
            Toast.makeText(requireContext(), "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show()
            return
        }

        val usuarioId = FirebaseAuth.getInstance().currentUser?.uid
        if (usuarioId == null) {
            Toast.makeText(requireContext(), "Usuário não autenticado!", Toast.LENGTH_SHORT).show()
            return
        }

        val chamado = hashMapOf(
            "usuarioId" to usuarioId,
            "nome" to nome,
            "telefone" to telefone,
            "email" to email,
            "descricao" to descricao,
            "dataHora" to dataHora,
            "latitude" to latitude,
            "longitude" to longitude,
//            imagemUri = imagemUri?.toString()
        )

        db.collection("chamados")
            .add(chamado)
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"chamado salvo com sucesso",Toast.LENGTH_LONG).show()

                
            }
            .addOnFailureListener {e ->
                Toast.makeText(requireContext(),"Erro ao salvar chamado:${e.message}", Toast.LENGTH_LONG).show()

            }
    }

//    private fun limparCampos() {
//        edtNome.text.clear()
//        edtTelefone.text.clear()
//        edtEmail.text.clear()
//        edtDescricao.text.clear()
//        txtLocalizacao.text = ""
//        latitude = ""
//        longitude = ""
//    }
}