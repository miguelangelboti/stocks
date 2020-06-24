package com.miguelangelboti.stocks.utils.extensions

import androidx.annotation.StringRes
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setErrorText(@StringRes resourceId: Int, enabled: Boolean) {
    error = context.getString(resourceId)
    isErrorEnabled = enabled
}

fun TextInputLayout.clearErrorWhenTextChanges() {
    editText?.doOnTextChanged { _, _, _, _ -> isErrorEnabled = false }
}
