package com.example.m_expenses.features.triplist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.m_expenses.R
import com.example.m_expenses.databinding.AdvancedSearchDialogBinding
import com.example.m_expenses.databinding.FragmentTripListBinding
import com.example.m_expenses.db.ExpenseDao
import com.example.m_expenses.db.TripDao
import com.example.m_expenses.models.SearchState
import com.example.m_expenses.models.Trip
import kotlinx.android.synthetic.main.fragment_trip_list.*


class TripListFragment : Fragment(), OnTripClickListener {

    private lateinit var binding: FragmentTripListBinding
    private var tripAdapter: TripListAdapter? = null

    private var tripDao: TripDao? = null
    private var expenseDao: ExpenseDao? = null
    private var tripList: List<Trip> = mutableListOf()

    private var lastSearchState: SearchState? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTripListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "M-Expenses | Trips"
        setHasOptionsMenu(true)

        if (lastSearchState == null)
            updateTripList()
        else searchForList(lastSearchState!!)

        tripAdapter = TripListAdapter(tripList, this)
        binding.tripRecyclerView.adapter = tripAdapter

        binding.addButton.setOnClickListener {
            findNavController().navigate(
                TripListFragmentDirections.actionTripListFragmentToAddtripFragment()
            )
        }

        binding.actionClose.setOnClickListener { etSearch.setText("") }
        binding.actionAdvanced.setOnClickListener { showAdvancedSearchDialog() }
        listenForSearch()
    }

    private fun showAdvancedSearchDialog() {
        val dialogBinding = AdvancedSearchDialogBinding.inflate(
            LayoutInflater.from(requireContext())
        )

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Advanced search")
            .setView(dialogBinding.root)
            .setNegativeButton("Close") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()

        dialog.show()


        dialogBinding.btnSearch.setOnClickListener {
            val name = dialogBinding.name.text.toString()
            val date = dialogBinding.date.text.toString()
            val destination = dialogBinding.destination.text.toString()

            if (name.isEmpty() && date.isEmpty() && destination.isEmpty()) {
                Toast.makeText(requireContext(), "please enter some key words", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            lastSearchState = SearchState(name, destination, date)
            searchForList(lastSearchState!!)
            dialog.dismiss()
        }
    }

    private var lastKeyName: String = ""
    private fun listenForSearch() {
        binding.etSearch.addTextChangedListener(afterTextChanged = {
            if (it.toString() != lastKeyName) {
                lastKeyName = it.toString()
                lastSearchState = SearchState(name = it.toString())
                searchForList(lastSearchState!!)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tripDao = TripDao.getInstance(requireContext())
        expenseDao = ExpenseDao.getInstance(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        tripDao?.clear()
        expenseDao?.clear()
    }


    private fun updateTripList() {
        tripList = tripDao?.getTrips()!!
        tripAdapter?.update(tripList)
    }

    private fun searchForList(searchState: SearchState) {
        tripList = tripDao?.search(searchState.name, searchState.destination, searchState.date)!!
        tripAdapter?.update(tripList)
    }


    override fun onTripClicked(tripId: Long) {
        findNavController().navigate(
            TripListFragmentDirections.actionTripListFragmentToTripDetailsFragment(tripId)
        )
    }

    override fun onDeleteTripClicked(tripId: Long) {
        tripDao?.remove(tripId)
        expenseDao?.removeAll(tripId)
        updateTripList()
    }

    override fun onEditTripClicked(tripId: Long) {
        findNavController().navigate(
            TripListFragmentDirections.actionTripListFragmentToAddtripFragment(tripId)
        )
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> {
                tripDao?.removeAll()
                expenseDao?.removeAll()
                updateTripList()
                true
            }
            R.id.action_search -> {
                binding.searchView.apply {
                    isVisible = !isVisible
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

