package hu.bme.aut.android.mood.data

import androidx.room.Entity

@Entity(primaryKeys = ["dailyEntryId","goalId"])
data class DailyEntryGoalsCrossRef(
    val dailyEntryId: Long,
    val goalId:Long
)