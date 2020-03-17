package com.htccs.android.neneyolka.base

import androidx.annotation.StringRes

class State<T> private constructor(
    val data: T? = null,
    val isLoading: Boolean = false,
    @StringRes val exceptionStringId: Int? = null,
    val exceptionString: String? = null
) {
    companion object {
        fun <T> empty() = State<T>()

        fun <T> loading() = State<T>(isLoading = true)

        fun <T> error(exceptionString: String) = State<T>(exceptionString = exceptionString)

        fun <T> error(@StringRes argExceptionStringId: Int) =
            State<T>(isLoading = false, exceptionStringId = argExceptionStringId)

        fun <T> setData(argData: T) = State(argData)
    }
}