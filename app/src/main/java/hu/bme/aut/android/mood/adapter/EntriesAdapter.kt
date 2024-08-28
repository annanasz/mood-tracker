package hu.bme.aut.android.mood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.mood.R
import hu.bme.aut.android.mood.data.DailyEntry
import hu.bme.aut.android.mood.databinding.DailyEntryBinding

class EntriesAdapter(private val listener: RecyclerViewItemClickListener) :
    RecyclerView.Adapter<EntriesAdapter.EntriesViewHolder>(){

    private var entries = mutableListOf<DailyEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EntriesViewHolder(
        DailyEntryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: EntriesViewHolder, position: Int) {
        val entry = entries[position]
        holder.binding.tvDate.text = entry.date
        holder.binding.ivIcon.setImageResource(getResource(entry.mood))
        holder.itemView.setOnClickListener{
            listener.onItemClick(entry)
        }
    }

    @DrawableRes
    private fun getResource(mood: DailyEntry.Mood): Int {
        return when(mood){
            DailyEntry.Mood.AWESOME -> R.drawable.awesome_cow
            DailyEntry.Mood.HAPPY -> R.drawable.happy_cow
            DailyEntry.Mood.GOOD -> R.drawable.good_cow
            DailyEntry.Mood.NEUTRAL -> R.drawable.neutral_cow
            DailyEntry.Mood.MOODY -> R.drawable.moody_cow
            DailyEntry.Mood.SICK -> R.drawable.sick_cow
            DailyEntry.Mood.SAD ->R.drawable.sad_cow
            DailyEntry.Mood.TERRIBLE ->R.drawable.terrible_cow
        }
    }

    override fun getItemCount(): Int = entries.size

    interface DailyEntryClickListener{
        fun onEntryChanged(entry: DailyEntry,goals:List<Long>)
        fun onEntryDeleted(entry: DailyEntry)
    }

    interface RecyclerViewItemClickListener{
        fun onItemClick(entry: DailyEntry)
    }

    inner class EntriesViewHolder(val binding: DailyEntryBinding) : RecyclerView.ViewHolder(binding.root)

    fun addEntry(entry:DailyEntry){
        entries.add(entry)
        notifyItemInserted(entries.size-1)
    }

    fun update(newEntries: List<DailyEntry>) {
        entries.clear()
        entries.addAll(newEntries)
        notifyDataSetChanged()
    }

    fun updateEntry(entry: DailyEntry){
        val entriescpy = mutableListOf<DailyEntry>()
        entriescpy.addAll(entries)
        for(e in entriescpy){
            if(e.dailyEntryId == entry.dailyEntryId)
            {
                val indx = entries.indexOf(e)
                entries.set(indx,entry)
            }
        }
        notifyItemChanged(entries.indexOf(entry))
    }

    fun delete(entry: DailyEntry){
        val index = entries.indexOf(entry);
        entries.remove(entry)
        notifyItemRemoved(index)
    }

}