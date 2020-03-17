package com.htccs.android.neneyolka.main.levelsFragment

import com.htccs.android.neneyolka.R
import com.htccs.android.neneyolka.base.State
import com.htccs.android.neneyolka.base.authorized.BaseAuthorizedViewModel
import com.htccs.android.neneyolka.data.LevelProvider
import com.htccs.android.neneyolka.data.model.level.Level
import com.htccs.android.neneyolka.data.model.user.User
import com.htccs.android.neneyolka.data.model.user.UserLevelInfo
import com.htccs.android.neneyolka.main.router.Router
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import org.koin.core.inject

class LevelsViewModel : BaseAuthorizedViewModel<LevelsState>() {

    private val levelProvider: LevelProvider by inject()
    private val router: Router by inject()

    private val levelsStateObservable = Observable.combineLatest(
        levelProvider.getLevels().toObservable(),
        userObservable,
        BiFunction<List<Level>, User, State<LevelsState>> { levels, user ->
            user.gameData
                ?.levelsInfo
                ?.let { State.setData(createLevelsState(levels, it)) }
                ?: throw IllegalStateException("user.gameData.levelsInfo not found")
        }
    )

    private fun createLevelsState(
        levels: List<Level>,
        levelsInfo: Map<String, UserLevelInfo>
    ): LevelsState {
        return levels
            .map { level -> levelsInfo.getLevelsItem(level.id.toString(), level) }
            .let { LevelsState(it) }
    }

    private fun Map<String, UserLevelInfo>.getLevelsItem(
        levelId: String,
        level: Level
    ): LevelsItem {
        return this[levelId]?.let { getLevelsItem(it, level) } ?: LevelsItem(level)
    }

    private fun getLevelsItem(levelInfo: UserLevelInfo, level: Level): LevelsItem {
        return if (levelInfo.positionInSequence != null
            && levelInfo.itemsSequence != null
            && levelInfo.qrCodeItems != null
        ) {
            LevelsItem(
                level,
                levelInfo.qrCodeItems.size,
                levelInfo.positionInSequence == levelInfo.itemsSequence.size
            )
        } else {
            throw IllegalStateException("user.gameData.levelsInfo doesn't have required fields")
        }
    }

    fun loadState() {
        levelsStateObservable.addSubscription(
            argOnNext = { state -> stateMutableLiveData.postValue(state) },
            argOnError = { exception ->
                exception.printStackTrace()
                stateMutableLiveData.postValue(State.error(R.string.levels_load_error))
            }
        )
    }

    fun openLevelFragment(level: Level) {
        level.id
            ?.let { router.openLevelFragment(it) }
            ?: stateMutableLiveData.postValue(State.error(R.string.levels_load_error))
    }
}