package com.htccs.android.neneyolka.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.htccs.android.neneyolka.notification.NotificationCaller
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {

    protected val toastCaller: NotificationCaller by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    abstract fun initView()

    protected fun startFragment(
        tag: String,
        containerId: Int = android.R.id.content,
        createFragment: () -> Fragment
    ) {
        (supportFragmentManager.findFragmentByTag(tag) ?: createFragment())
            .run { replaceFragment(this, tag, containerId) }
    }

    private fun replaceFragment(fragment: Fragment, fragmentTag: String, containerId: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                containerId,
                fragment,
                fragmentTag
            )
            .addToBackStack(null)
            .commit()
    }

    fun sendMessage(context: Context, text: String) {
        toastCaller.showMessage(context, text)
    }
}