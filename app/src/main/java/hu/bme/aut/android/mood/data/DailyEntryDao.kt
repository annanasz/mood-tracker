package hu.bme.aut.android.mood.data

import androidx.room.*

@Dao
interface DailyEntryDao {
    @Query("SELECT * FROM dailyentries")
    fun getAllDailyEntry(): List<DailyEntry>

    @Query("SELECT * FROM dailyentries WHERE dailyEntryId=:id")
    fun getDailyEntry(id:Long): DailyEntry

    @Query("SELECT * FROM dailyentries WHERE date=:d")
    fun getDailyEntryByDate(d:String): List<DailyEntry>

    @Insert
    fun insertDailyEntry(dailyEntry: DailyEntry): Long

    @Update
    fun updateDailyEntry(dailyEntry: DailyEntry)

    @Delete
    fun deleteDailyEntry(dailyEntry: DailyEntry)

    @Query("SELECT * FROM goals")
    fun getAllGoal(): List<Goal>

    @Query("SELECT * FROM goals WHERE goalId=:id")
    fun getGoal(id:Long): Goal

    @Insert
    fun insertGoal(goal: Goal): Long

    @Update
    fun updateGoal(goal: Goal)

    @Delete
    fun deleteGoal(goal: Goal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDailyEntryGoalCrossRef(crossRef: DailyEntryGoalsCrossRef)

    @Query("SELECT * FROM dailyentries WHERE dailyEntryId=:entryId")
    fun getGoalsOfDailyEntry(entryId:Long): List<DailyEntryWithGoal>

    @Query("SELECT * FROM goals WHERE goalId=:goalId")
    fun getDailyEntriesOfGoal(goalId:Long): List<GoalsWithDailyEntries>

    @Query("DELETE FROM dailyentrygoalscrossref WHERE dailyEntryId=:entryId")
    fun deleteCrossRefByEntry(entryId: Long)

    @Query("DELETE FROM dailyentrygoalscrossref WHERE goalId = :goalId")
    fun deleteCrossRefByGoal(goalId: Long)
}