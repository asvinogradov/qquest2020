package com.htccs.android.neneyolka.base.authorized

import com.htccs.android.neneyolka.base.BaseNetworkViewModel
import com.htccs.android.neneyolka.data.model.user.User
import com.htccs.android.neneyolka.module.USER_OBSERVABLE_KEY
import io.reactivex.Observable
import org.koin.core.get
import org.koin.core.qualifier.named

open class BaseAuthorizedViewModel<T> : BaseNetworkViewModel<T>() {

    val userObservable: Observable<User> = get(named(USER_OBSERVABLE_KEY))
}