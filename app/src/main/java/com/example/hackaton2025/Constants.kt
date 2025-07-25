package com.example.hackaton2025

object Constants {
    const val CURRENCY_NAME = "Euro"
    const val CURRENCY_SIGN = "€"

    const val TASK_DEADLINE_FORMAT = "MMMM dd, yyyy 'at' h a"

    fun amountWithCurrency(amount: Int): String = "$CURRENCY_SIGN $amount"
}