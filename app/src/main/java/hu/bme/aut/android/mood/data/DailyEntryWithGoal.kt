package hu.bme.aut.android.mood.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class DailyEntryWithGoal (
    @Embedded val dailyEntry:DailyEntry,
    @Relation(
        parentColumn = "dailyEntryId",
        entityColumn = "goalId",
        associateBy = Junction(DailyEntryGoalsCrossRef::class)
    )
    val goals: List<Goal>
)