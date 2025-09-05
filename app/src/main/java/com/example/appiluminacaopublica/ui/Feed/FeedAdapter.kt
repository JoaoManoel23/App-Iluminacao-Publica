package com.example.appiluminacaopublica.ui.Feed
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appiluminacaopublica.R
import com.example.appiluminacaopublica.data.model.Chamado
import com.example.appiluminacaopublica.data.model.FeedPost
import java.text.SimpleDateFormat
import java.util.Locale


class FeedAdapter (private val feedList: List<Chamado>) :
    RecyclerView.Adapter<FeedAdapter.ChamadoViewHolder>() {

        class ChamadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textUser: TextView = itemView.findViewById(R.id.textUser)
            val textTime: TextView = itemView.findViewById(R.id.textTime)
            val textPostContent: TextView = itemView.findViewById(R.id.textPostContent)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChamadoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_post, parent, false)
            return ChamadoViewHolder(view)
        }

        override fun onBindViewHolder(holder: ChamadoViewHolder, position: Int) {
            val chamado = feedList[position]
            holder.textUser.text = chamado.nome
            holder.textTime.text = chamado.dataHora ?: "Sem data"
//            holder.textTime.text= chamado.dataHora?.let {
//                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
//                sdf.format(it.toDate())
//            } ?: "Sem data"

            holder.textPostContent.text = chamado.descricao
        }

        override fun getItemCount(): Int = feedList.size
}