package hu.bme.aut.android.mood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.mood.R
import hu.bme.aut.android.mood.data.DailyEntry
import hu.bme.aut.android.mood.databinding.EntryMiniItemBinding

class MoodAdapter : RecyclerView.Adapter<MoodAdapter.MoodViewHolder>(){


    private var entries = mutableListOf<DailyEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=MoodViewHolder(
        EntryMiniItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )
    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        val entry = entries[position]
        holder.binding.ivIcon.setImageResource(getResource(entry.mood))
        holder.binding.tvMood.text = getMoodString(entry.mood)
    }

    override fun getItemCount(): Int = entries.size

    inner class MoodViewHolder(val binding: EntryMiniItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun update(newEntries: List<DailyEntry>) {
        entries.clear()
        entries.addAll(newEntries)
        notifyDataSetChanged()
    }

    @DrawableRes
    private fun getResource(mood: DailyEntry.Mood): Int {
        return when(mood){
            DailyEntry.Mood.AWESOME -> R.drawable.ic_awesomeradio
            DailyEntry.Mood.HAPPY -> R.drawable.ic_happyradio
            DailyEntry.Mood.GOOD -> R.drawable.ic_goodradio
            DailyEntry.Mood.NEUTRAL -> R.drawable.ic_neutralradio
            DailyEntry.Mood.MOODY -> R.drawable.ic_moodyradio
            DailyEntry.Mood.SICK -> R.drawable.ic_sickradio
            DailyEntry.Mood.SAD -> R.drawable.ic_sadradio
            DailyEntry.Mood.TERRIBLE -> R.drawable.ic_terribleradio
        }
    }

    private fun getMoodString(mood: DailyEntry.Mood): String {
        return when(mood){
            DailyEntry.Mood.AWESOME -> "Awesome"
            DailyEntry.Mood.HAPPY -> "Happy"
            DailyEntry.Mood.GOOD -> "Good"
            DailyEntry.Mood.NEUTRAL -> "Neutral"
            DailyEntry.Mood.MOODY -> "Moody"
            DailyEntry.Mood.SICK -> "Sick"
            DailyEntry.Mood.SAD -> "Sad"
            DailyEntry.Mood.TERRIBLE -> "Terrible"
        }
    }
}