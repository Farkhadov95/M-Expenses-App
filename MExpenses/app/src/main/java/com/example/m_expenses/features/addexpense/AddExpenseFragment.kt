package com.example.m_expenses.features.addexpense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.m_expenses.databinding.FragmentAddExpenseBinding
import com.example.m_expenses.db.ExpenseDao
import com.example.m_expenses.models.Expense

class AddExpenseFragment : Fragment() {

    private val args: AddExpenseFragmentArgs by navArgs()
    private var expenseDao: ExpenseDao? = null

    private lateinit var binding: FragmentAddExpenseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expenseDao = ExpenseDao.getInstance(requireContext())


        if (args.expenseId != -1L) {
            prepareExpenseDetails()
        } else if (args.date.isNotEmpty())
            binding.expItemDate.setText(args.date)

        binding.btnSave.setOnClickListener {
            saveExpense()
        }
    }

    private fun prepareExpenseDetails() {
        val expense = expenseDao?.getExpenseById(args.expenseId)

        if (expense == null) {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
            return
        }

        with(binding) {
            expItemType.setText(expense.type)
            expItemDate.setText(expense.time)
            expItemAmount.setText(expense.amount)
            expItemComment.setText(expense.comment)
        }

    }

    private fun saveExpense() {
        val type = binding.expItemType.text.toString()
        val time = binding.expItemDate.text.toString()
        val amount = binding.expItemAmount.text.toString()
        val comment = binding.expItemComment.text.toString()

        if (type.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter the expense type", Toast.LENGTH_LONG)
                .show()
            return
        }

        if (time.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter the expense date", Toast.LENGTH_LONG)
                .show()
            return
        }

        if (amount.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Please enter the expense amount",
                Toast.LENGTH_LONG
            )
                .show()
            return
        }

        val expense = Expense(
            tripId = args.tripId,
            type = type,
            amount = amount,
            time = time,
            comment = comment
        )

        if (args.expenseId != -1L) {
            expenseDao?.update(args.expenseId, expense)
        } else {
            expenseDao?.saveExpense(expense)
        }
        findNavController().popBackStack()

    }

}