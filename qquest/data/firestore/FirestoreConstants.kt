package com.htccs.android.neneyolka.data.firestore

object FirestoreConstants {
    const val LEVELS_COLLECTION = "levels"
    const val ITEMS_COLLECTION = "items"
    const val USERS_COLLECTION = "users"
    const val SPECIAL_ITEMS_COLLECTION = "special_items"
    const val RECIEVED_ITEMS_COLLECTION = "received_items"
    private const val GAME_DATA_FIELD = "gameData"
    const val INVENTORY_ITEMS_FIELD = "$GAME_DATA_FIELD.inventory.inventoryItems"
    const val SCORE_FIELD = "$GAME_DATA_FIELD.score"
    const val SCANNED_QRS_FIELD = "$GAME_DATA_FIELD.scannedQrs"
    const val LEVELS_INFO_FIELD = "$GAME_DATA_FIELD.levelsInfo."
    const val POSITION_IN_SEQUENCE_FIELD = ".positionInSequence"
    const val GAME_STARTED_FIELD = ".gameStarted"

    const val THROW_ITEM = "throwItem"
    const val UPDATE_LOGIN = "updateLogin"
    const val PROCESS_TRAINING_LEVEL_QR = "processTrainingLevelQr"
    const val PROCESS_INVENTORY_QR = "processInventoryQr"
    const val PROCESS_LEVEL_QR = "processLevelQr"
    const val DROP_LEVEL_PROGRESS = "dropLevelProgress"
    const val OPEN_GIFT = "openGift"

    const val TARGET_EMAIL = "targetEmail"
    const val THROWN_ITEM = "thrownItem"
    const val LOGIN = "login"
    const val QR = "qr"
    const val LEVEL_ID = "levelId"
}