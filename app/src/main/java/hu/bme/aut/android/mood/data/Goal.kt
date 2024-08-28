package hu.bme.aut.android.mood.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "goals")
data class Goal (
    @ColumnInfo(name = "goalId") @PrimaryKey(autoGenerate = true) var goalId:Long?=null,
    @ColumnInfo(name = "name") var name:String,
    @ColumnInfo(name = "description") var description:String,
    @ColumnInfo(name = "category") var category:GoalCategory
    ):Parcelable{
    @Parcelize
    enum class GoalCategory:Parcelable{
        LIFESTYLE, SELFCARE, STUDIES, HEALTH, FINANCIAL, OTHER;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): GoalCategory? {
                var ret: GoalCategory? = null
                for (category in values()) {
                    if (category.ordinal == ordinal) {
                        ret = category
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(category: GoalCategory): Int {
                return category.ordinal
            }
        }
    }
}