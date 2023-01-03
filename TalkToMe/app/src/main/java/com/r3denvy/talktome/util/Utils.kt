package com.r3denvy.talktome.util

import android.app.Activity
import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    fun clearFocusAndHideKeyboard(fragment: Fragment, view1: View?, view2: View?, view3: View?) {
        fragment.hideKeyboard()
        view1?.clearFocus()
        view2?.clearFocus()
        view3?.clearFocus()
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isValidEmail(target: CharSequence): Boolean = Patterns.EMAIL_ADDRESS.matcher(target).matches()

    fun getTime(): String = SimpleDateFormat("d MMM\nh:mm aa").format(Date())

    fun getTime(pattern: String): String = SimpleDateFormat(pattern).format(Date())

}