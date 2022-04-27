package com.example.m_expenses.features.tripdetails

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.m_expenses.R
import com.example.m_expenses.databinding.FragmentExpensesBinding
import com.example.m_expenses.db.ExpenseDao
import com.example.m_expenses.db.TripDao
import com.example.m_expenses.models.Expense
import com.example.m_expenses.models.Trip
import com.example.m_expenses.network.*
import com.google.gson.Gson
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ExpensesFragment : Fragment(), OnExpenseClickListener {

    private val args: ExpensesFragmentArgs by navArgs()
    private var currentTrip: Trip? = null
    private var expenseList: List<Expense> = mutableListOf()
    private var tripDao: TripDao? = null
    private var expenseDao: ExpenseDao? = null

    private lateinit var binding: FragmentExpensesBinding

    private lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = AlertDialog.Builder(requireContext())
            .setMessage("Loading, please wait...")
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "M-Expenses | Expenses"
        setHasOptionsMenu(true)

        tripDao = TripDao.getInstance(requireContext())
        expenseDao = ExpenseDao.getInstance(requireContext())

        binding.addButton.setOnClickListener {
            findNavController().navigate(
                ExpensesFragmentDirections.actionTripDetailsFragmentToAddExpenseFragment(
                    tripId = args.tripId,
                    date = currentTrip?.date ?: ""
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        getTripDetails()
    }

    private fun getTripDetails() {
        currentTrip = tripDao?.getTripById(args.tripId)

        if (currentTrip == null) {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            return
        }

        binding.itemTitle.text = currentTrip!!.name

        getExpenseList()

    }

    private fun getExpenseList() {
        expenseList = expenseDao?.getExpenses(args.tripId)!!
        binding.expenseList.adapter = ExpenseAdapter(expenseList, this)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.trip_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_upload) {
            upload()
            return true
        } else super.onOptionsItemSelected(item)
    }

    private fun upload() {
        val request = UploadRequest(detailList = expenseList.toRequestDetails(currentTrip!!.name))
        progressDialog.show()

        getApi().upload(Gson().toJson(request)).enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                progressDialog.dismiss()
                if (response.isSuccessful && response.body() != null) {
                    showSuccessDialog(response.body()!!)
                } else {
                    showErrorDialog()
                }
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                progressDialog.dismiss()
                showErrorDialog()
            }

        })

    }

    private fun showErrorDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Error!")
            .setMessage("Error occurred, please try again")
            .setPositiveButton("Ok") { _, _ -> }.show()
    }

    private fun showSuccessDialog(body: UploadResponse) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hola!")
            .setMessage(body.readable())
            .setPositiveButton("Ok") { _, _ -> }.show()
    }


    private fun getApi(): Api {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl("http://gillwindallapp1.appspot.com")
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .connectionPool(ConnectionPool(5, 5, TimeUnit.SECONDS))
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            ).build()

        return retrofit.create(Api::class.java)
    }

    override fun onDeleteExpenseClicked(expenseId: Long) {
        expenseDao!!.removeExpense(expenseId)
        getExpenseList()
    }

    override fun onEditExpenseClicked(expenseId: Long) {
        findNavController().navigate(
            ExpensesFragmentDirections.actionTripDetailsFragmentToAddExpenseFragment(
                expenseId = expenseId,
                tripId = args.tripId,
                date = ""
            )
        )
    }
}