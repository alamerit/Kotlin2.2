package ru.geekbrains.gb_kotlin.common

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(format: String) = SimpleDateFormat(format, Locale.getDefault()).format(this)