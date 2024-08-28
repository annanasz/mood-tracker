package hu.bme.aut.android.mood.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GoalsWithDailyEntries (
    @Embedded val goal:Goal,
    @Relation(
        parentColumn = "goalId",
        entityColumn = "dailyEntryId",
        associateBy = Junction(DailyEntryGoalsCrossRef::class)
    )
    val entries: List<DailyEntry>
    )