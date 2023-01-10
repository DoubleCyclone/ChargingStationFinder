package com.project.chargingstationfinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.project.chargingstationfinder.model.Connections

class DetailsAdapter(private val detailsList: List<Connections>) :
    RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {

    inner class DetailsViewHolder(private val detailsView: View) :
        RecyclerView.ViewHolder(detailsView) {
        fun bindData(connection: Connections) {
            val id = itemView.findViewById<TextView>(R.id.name)

            id.text = connection.ID.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        return DetailsViewHolder(LayoutInflater.from(parent.context ).inflate(R.layout.rv_details,parent,false))
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.bindData(detailsList[position])
    }

    override fun getItemCount(): Int {
        return detailsList.size
    }
}