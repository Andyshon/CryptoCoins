package com.andyshon.cryptocoins.utils.extensions

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.LayoutRes
import java.text.DecimalFormat

fun EditText.fetchText(): String = this.text.toString().trim()

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun EditText.hideKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

infix fun ViewGroup.inflate(@LayoutRes id: Int): View {
    return LayoutInflater.from(context).inflate(id, this, false)
}

fun TextView.setColouredText(str: String, format: String, price: Double) {

    fun decFormat(format: String, price: Double?): String {
        val df = DecimalFormat(format)
        return df.format(price)
    }

    val spannableString = SpannableString(str + decFormat(format, price))
    if (price > 0) {
        spannableString.setSpan(ForegroundColorSpan(Color.GREEN), str.length, spannableString.length, 0)
    } else {
        spannableString.setSpan(ForegroundColorSpan(Color.RED), str.length, spannableString.length, 0)
    }
    this.text = spannableString
}

fun TextView.decFormat(format: String, price: Double?) {
    val df = DecimalFormat(format)
    this.text = df.format(price)
}