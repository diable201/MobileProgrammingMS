package com.example.finalproject.utils

fun Double.toCurrency(): String {
    return "$${"%.2f".format(this)}"
}
