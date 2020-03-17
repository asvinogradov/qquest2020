package com.htccs.android.neneyolka.data.model.level

data class Level(
    val id: Int? = null,
    val name: String? = null,
    val imageReference: String? = null,
    val isLastLevel: Boolean? = null,
    val type: LevelType? = null,
    val levelBounty: Int? = null,
    val goToQuestionText: String? = null,
    val itemsCount: Int? = null,
    val description: String? = null
)