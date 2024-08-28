package hu.bme.aut.android.mood.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        DailyEntry::class,
        Goal::class,
        DailyEntryGoalsCrossRef::class], version=3)
@TypeConverters(value = [DailyEntry.Mood::class, Goal.GoalCategory::class])
abstract class DailyEntryDatabase : RoomDatabase() {
    abstract fun dailyEntryDao() : DailyEntryDao

    companion object {
        @Volatile
        private var INSTANCE: DailyEntryDatabase? = null
        fun getDatabase(applicationContext: Context): DailyEntryDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    applicationContext,
                    DailyEntryDatabase::class.java,
                    "entries-mood"
                ).fallbackToDestructiveMigration().build().also {
                    INSTANCE=it
                }
            }
        }
    }
}