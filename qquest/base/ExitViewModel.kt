package com.htccs.android.neneyolka.base

import android.os.Handler

private const val DOUBLE_TAP_DELAY_MILLIS = 2000L
private const val EMPTY_MESSAGE = 0

class ExitViewModel : BaseViewModel() {

    private var exitFlag = false
    private val handler: Handler = Handler {
        exitFlag = false
        true
    }

    fun checkBackDoubleTap(): Boolean {
        return if (exitFlag) {
            true
        } else {
            exitFlag = true
            handler.sendEmptyMessageDelayed(EMPTY_MESSAGE, DOUBLE_TAP_DELAY_MILLIS)
            false
        }
    }
}