package com.example.m_expenses.features.tripdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.m_expenses.databinding.ListItemExpenseBinding
import com.example.m_expenses.models.Expense

class ExpenseAdapter(val list: List<Expense>, val onExpenseClickListener: OnExpenseClickListener) :
    RecyclerView.Adapter<ExpenseAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ListItemExpenseBinding.inflate(
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

    inner class Holder(private val binding: ListItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val expense = list[adapterPosition]

            with(binding) {
                expItemType.text = expense.type
                expItemAmount.text = expense.amount
                expItemDate.text = expense.time
                expItemComment.text = expense.comment

                itemDeleteBtn.setOnClickListener {
                    onExpenseClickListener.onDeleteExpenseClicked(
                        expense.id!!
                    )
                }
                itemEditBtn.setOnClickListener { onExpenseClickListener.onEditExpenseClicked(expense.id!!) }
            }
        }
    }
}