package com.r3denvy.talktome.util

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment


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

}