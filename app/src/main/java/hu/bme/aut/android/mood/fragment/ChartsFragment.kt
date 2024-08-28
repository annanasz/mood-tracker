package hu.bme.aut.android.mood.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import hu.bme.aut.android.mood.data.DailyEntry
import hu.bme.aut.android.mood.data.DailyEntryDatabase
import hu.bme.aut.android.mood.databinding.FragmentChartsBinding
import kotlin.concurrent.thread

class ChartsFragment : Fragment() {

    private lateinit var binding: FragmentChartsBinding
    private lateinit var database:DailyEntryDatabase
    private var moods = mutableListOf(0F,0F,0F,0F,0F,0F,0F,0F)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChartsBinding.inflate(inflater, container, false)
        database = DailyEntryDatabase.getDatabase(requireContext().applicationContext)
        loadData()
        return binding.root;
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun clearMoods(){
        moods = mutableListOf(0F,0F,0F,0F,0F,0F,0F,0F)
    }

    private fun loadData(){
        thread {
            clearMoods()
            val items = database.dailyEntryDao().getAllDailyEntry()
            for (i in items) {
                moods[i.mood.ordinal] += 1F
            }
            requireActivity().runOnUiThread { makePieChart() }
        }
    }

    private fun makePieChart(){
        val entries = mutableListOf<PieEntry>()
        for(i in 0 until moods.size){
            if(moods[i]>0f) {
                val entry =  PieEntry(moods[i],getMoodString(i))
                entries.add(entry)
            }
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = mutableListOf(Color.rgb(192,0,0), Color.rgb(255,0,0), Color.rgb(255,192,0),Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189),Color.rgb(255,192,203))

        val data = PieData(dataSet)
        binding.chartHoliday.data = data
        binding.chartHoliday.data.setValueTextSize(20f)
        binding.chartHoliday.setEntryLabelColor(Color.BLACK)
        binding.chartHoliday.setEntryLabelTextSize(20f)
        binding.chartHoliday.legend.isEnabled = false
        binding.chartHoliday.description.isEnabled=false
        binding.chartHoliday.invalidate()
    }

    fun getByOrdinal(ordinal: Int): DailyEntry.Mood? {
        var ret: DailyEntry.Mood? = null
        for (mood in DailyEntry.Mood.values()) {
            if (mood.ordinal == ordinal) {
                ret = mood
                break
            }
        }
        return ret
    }

    private fun getMoodString(moodordinal: Int): String {
        return when(getByOrdinal(moodordinal)){
            DailyEntry.Mood.AWESOME -> "Awesome"
            DailyEntry.Mood.HAPPY -> "Happy"
            DailyEntry.Mood.GOOD -> "Good"
            DailyEntry.Mood.NEUTRAL -> "Neutral"
            DailyEntry.Mood.MOODY -> "Moody"
            DailyEntry.Mood.SICK -> "Sick"
            DailyEntry.Mood.SAD -> "Sad"
            DailyEntry.Mood.TERRIBLE -> "Terrible"
            else -> {"Awesome"}
        }
    }

}