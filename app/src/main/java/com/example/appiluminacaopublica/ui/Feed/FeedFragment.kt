package com.example.appiluminacaopublica.ui.Feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appiluminacaopublica.R
import com.example.appiluminacaopublica.data.model.Chamado
import com.example.appiluminacaopublica.data.model.FeedPost
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

class FeedFragment : Fragment() {

    private lateinit var recyclerFeed: RecyclerView
    private lateinit var feedAdapter: FeedAdapter
    private val feedList = mutableListOf<Chamado>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerFeed = view.findViewById(R.id.recyclerFeed)
        recyclerFeed.layoutManager = LinearLayoutManager(requireContext())

        feedAdapter = FeedAdapter(feedList)
        recyclerFeed.adapter = feedAdapter

        loadFakeFeed()
    }

    private fun loadFakeFeed() {
        FirebaseFirestore.getInstance()
            .collection("chamados")
            .orderBy("dataHora",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                feedList.clear()
                for(document in result){
                    val chamado = document.toObject(Chamado::class.java)
                    feedList.add(chamado)
                }
                feedAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Erro ao carregar chamados", Toast.LENGTH_SHORT).show()
            }
//        feedList.add(
//            FeedPost(
//                user = "Pedro Augusto, Capão da Canoa",
//                time = "20m",
//                content = "Venho aqui parabenizar a solução de um problema que estava acontecendo na minha rua por meses."
//            )
//        )
//        feedList.add(
//            FeedPost(
//                user = "Maria Joan",
//                time = "40m",
//                content = "Gostaria de agradecer pela troca rápida da lâmpada queimada em frente à minha casa!"
//            )
//        )
//        feedList.add(
//            FeedPost(
//                user = "Maria Joan",
//                time = "40m",
//                content = "Gostaria de agradecer pela troca rápida da lâmpada queimada em frente à minha casa!"
//            )
//        )
//        feedList.add(
//            FeedPost(
//                user = "Pedro Augusto, Capão da Canoa",
//                time = "20m",
//                content = "Venho aqui parabenizar a solução de um problema que estava acontecendo na minha rua por meses."
//            )
//        )

//        feedAdapter.notifyDataSetChanged()
    }
}