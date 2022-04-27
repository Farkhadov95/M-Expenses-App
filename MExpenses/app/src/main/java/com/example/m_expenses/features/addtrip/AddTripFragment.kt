package com.example.m_expenses.features.addtrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.m_expenses.databinding.FragmentAddTripBinding
import com.example.m_expenses.db.TripDao
import com.example.m_expenses.models.Trip
import java.text.SimpleDateFormat
import java.util.*

class AddTripFragment : Fragment() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyy", Locale.getDefault())

    private val args: AddTripFragmentArgs by navArgs()
    private var tripDao: TripDao? = null

    private lateinit var binding: FragmentAddTripBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tripDao = TripDao.getInstance(requireContext())

        binding.btnSave.setOnClickListener {
            saveTrip()
        }

        if (args.tripId != -1L)
            putTripDetails()
        else {
            binding.itemDate.setText(dateFormat.format(Date()))
        }
    }


    private fun putTripDetails() {
        val trip = tripDao?.getTripById(args.tripId)
        if (trip == null) {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            return
        }

        with(binding) {
            itemTitle.setText(trip.name)
            itemDate.setText(trip.date)
            itemDescription.setText(trip.description)
            itemDestination.setText(trip.destination)
            itemRisk.isChecked = trip.requireRisk
        }
    }


    private fun saveTrip() {
        val name = binding.itemTitle.text.toString()
        val date = binding.itemDate.text.toString()
        val destination = binding.itemDestination.text.toString()
        val description = binding.itemDescription.text.toString()

        if (name.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter the trip name", Toast.LENGTH_LONG)
                .show()
            return
        }

        if (date.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter the trip date", Toast.LENGTH_LONG)
                .show()
            return
        }

        if (destination.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Please enter the trip destination",
                Toast.LENGTH_LONG
            )
                .show()
            return
        }

        val trip = Trip(
            name = name,
            date = date,
            destination = destination,
            description = description,
            requireRisk = binding.itemRisk.isChecked
        )

        if (args.tripId != -1L) {
            tripDao?.update(args.tripId, trip)
        } else {
            tripDao?.saveTrip(trip)
        }

        findNavController().popBackStack()
    }

}