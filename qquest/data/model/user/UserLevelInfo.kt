package com.htccs.android.neneyolka.data.model.user

data class UserLevelInfo(
    val itemsSequence: List<Int>? = null,
    val positionInSequence: Int? = null,
    val qrCodeItems: List<String>? = null,
    val gameState: GameState? = null
)