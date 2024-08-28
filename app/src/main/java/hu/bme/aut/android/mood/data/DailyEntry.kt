package hu.bme.aut.android.mood.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "dailyentries")
data class DailyEntry (
    @ColumnInfo(name = "dailyEntryId") @PrimaryKey(autoGenerate = true) var dailyEntryId: Long? = null,
    @ColumnInfo(name = "mood") var mood: Mood,
    @ColumnInfo(name = "date") var date:String,
    @ColumnInfo(name = "positive") var positiveThings: String,
    @ColumnInfo(name = "negative") var negativeThings: String,
    @ColumnInfo(name = "thoughts") var thoughts:String,
    @ColumnInfo(name = "image") var image:String
): Parcelable {
    @Parcelize
    enum class Mood:Parcelable{
        AWESOME, HAPPY, GOOD, NEUTRAL, SAD, TERRIBLE, SICK, MOODY;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Mood? {
                var ret: Mood? = null
                for (mood in values()) {
                    if (mood.ordinal == ordinal) {
                        ret = mood
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(mood: Mood): Int {
                return mood.ordinal
            }
        }
    }
}