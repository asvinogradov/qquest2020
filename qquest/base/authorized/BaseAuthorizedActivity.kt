package com.htccs.android.neneyolka.base.authorized

import android.os.Bundle
import androidx.lifecycle.Observer
import com.htccs.android.neneyolka.authentication.AuthenticationActivity
import com.htccs.android.neneyolka.base.ExitableActivity

abstract class BaseAuthorizedActivity : ExitableActivity() {

    abstract val mainViewModel: BaseAuthorizedActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel
            .isAuthorizationNeededLiveData
            .observe(this, Observer { isAuthorizationNeeded ->
                if (isAuthorizationNeeded) {
                    AuthenticationActivity.start(this)
                    finish()
                }
            })
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.onCheckAuthorization()
    }
}