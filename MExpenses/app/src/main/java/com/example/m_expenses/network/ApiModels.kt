package com.example.m_expenses.network

import com.example.m_expenses.models.Expense

data class UploadRequest(
    val userId: String = "wm123",
    val detailList: List<Detail>
)

data class UploadResponse(
    val uploadResponseCode: String,
    val userid: String,
    val number: Int,
    val names: String,
    val message: String
)


data class Detail(
    val name: String,
    val type: String,
    val amount: String,
    val time: String,
)


fun List<Expense>.toRequestDetails(tripName: String): List<Detail> {
    val list = mutableListOf<Detail>()

    forEach {
        list.add(
            Detail(
                tripName,
                it.type,
                it.amount,
                it.time
            )
        )
    }

    return list
}


fun UploadResponse.readable() =
    "Response code: $uploadResponseCode\n" +
            "user: $userid\n" +
            "names $names\n" +
            "message $message\n" +
            "numbers $number"