package hu.bme.aut.android.mood.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.mood.adapter.MoodAdapter
import hu.bme.aut.android.mood.data.DailyEntryDatabase
import hu.bme.aut.android.mood.databinding.FragmentCalendarBinding
import java.util.*
import kotlin.concurrent.thread

class CalendarFragment : Fragment() {

    private lateinit var binding:FragmentCalendarBinding
    private lateinit var database:DailyEntryDatabase
    private lateinit var adapter:MoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        database = DailyEntryDatabase.getDatabase(requireContext().applicationContext)

        initRecyclerView()
        binding.calendarView
            .setOnDateChangeListener { _, year, month, dayOfMonth ->
                val date = String.format(
                    Locale.getDefault(), "%04d.%02d.%02d.",
                    year, month + 1, dayOfMonth
                )
                loadItemsInBackground(date)
            }
        return binding.root
    }

     override fun onResume() {
        super.onResume()

        val calendar = Calendar.getInstance()
         binding.calendarView.date = calendar.timeInMillis
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val date = String.format(
            Locale.getDefault(), "%04d.%02d.%02d.",
            year, month + 1, day
        )
        loadItemsInBackground(date)
    }

    private fun initRecyclerView() {
        adapter = MoodAdapter()
        binding.rvMain.layoutManager = LinearLayoutManager(this.context)
        binding.rvMain.adapter = adapter
    }

    private fun loadItemsInBackground(date: String){
        thread{
            val items = database.dailyEntryDao().getDailyEntryByDate(date)
            requireActivity().runOnUiThread {
                adapter.update(items)
            }
        }
    }

}