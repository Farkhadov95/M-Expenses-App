package com.example.m_expenses.features.triplist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.m_expenses.databinding.ListItemTripBinding
import com.example.m_expenses.models.Trip

class TripListAdapter(var list: List<Trip>, val listener: OnTripClickListener) :
    RecyclerView.Adapter<TripListAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ListItemTripBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(tripList: List<Trip>) {
        list = tripList
        notifyDataSetChanged()
    }


    inner class Holder(private val binding: ListItemTripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val trip = list[adapterPosition]
            with(binding) {
                itemTitle.text = trip.name
                itemDestination.text = trip.destination
                itemDescription.text = trip.description
                itemRisk.isChecked = trip.requireRisk
                itemDate.text = trip.date
            }

            binding.itemEditBtn.setOnClickListener {
                listener.onEditTripClicked(trip.id!!)
            }

            binding.itemDeleteBtn.setOnClickListener {
                listener.onDeleteTripClicked(trip.id!!)
            }

            binding.root.setOnClickListener {
                listener.onTripClicked(trip.id!!)
            }
        }
    }
}