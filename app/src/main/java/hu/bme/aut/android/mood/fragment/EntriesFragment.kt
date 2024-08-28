package hu.bme.aut.android.mood.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.mood.AddEntryActivity
import hu.bme.aut.android.mood.EntryDetailsActivity
import hu.bme.aut.android.mood.adapter.EntriesAdapter
import hu.bme.aut.android.mood.data.*
import hu.bme.aut.android.mood.databinding.FragmentEntriesBinding
import kotlin.concurrent.thread

class EntriesFragment : Fragment(), EntriesAdapter.DailyEntryClickListener, AddEntryActivity.AddEntryListener, EntriesAdapter.RecyclerViewItemClickListener {

    private lateinit var binding: FragmentEntriesBinding
    private lateinit var adapter: EntriesAdapter
    private lateinit var database: DailyEntryDatabase

    private val getContent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode != AddEntryActivity.CANCEL) {
            val goals = mutableListOf<Long>()
            val entry:DailyEntry = it.data?.getParcelableExtra(AddEntryActivity.NEW_ENTRY)!!
            val actualgoals:ArrayList<Goal> = it.data?.getParcelableArrayListExtra(AddEntryActivity.SELECTED_GOALS)!!
            for(goal in actualgoals){
                goals.add(goal.goalId!!)
            }
            if (it.resultCode == AddEntryActivity.SAVE) {
                OnEntryAdded(entry,goals)
            } else if (it.resultCode == AddEntryActivity.EDIT) {
                onEntryChanged(entry,goals)
            }
        }
    }

    private val deleteOrUpdateContent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == EntryDetailsActivity.DELETE){
            val entry:DailyEntry = it.data?.getParcelableExtra(EntryDetailsActivity.ENTRY_TO_DELETE)!!
            onEntryDeleted(entry)
        }
        if(it.resultCode == EntryDetailsActivity.EDIT){
            val int = Intent(this.context,AddEntryActivity::class.java)
            int.putExtras(it.data!!.extras!!)
            getContent.launch(int)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntriesBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener {
            val intent = Intent(this.context, AddEntryActivity::class.java)
            thread {
                val goals = database.dailyEntryDao().getAllGoal()
                val array:ArrayList<Goal> = ArrayList(goals)
                intent.putParcelableArrayListExtra(AddEntryActivity.ALL_GOALS,array)
                getContent.launch(intent)
            }
        }
        database = DailyEntryDatabase.getDatabase(this.requireContext().applicationContext)
        initRecyclerView()
        return binding.root;
    }

    private fun initRecyclerView() {
        adapter = EntriesAdapter(this)
        binding.rvMain.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.rvMain.layoutManager = LinearLayoutManager(this.context)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.dailyEntryDao().getAllDailyEntry()
            requireActivity().runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onEntryChanged(entry: DailyEntry,goals:List<Long>) {
        thread {
            database.dailyEntryDao().updateDailyEntry(entry)
            database.dailyEntryDao().deleteCrossRefByEntry(entry.dailyEntryId!!)
            for(goal in goals){
                val goalid:Long = goal
                val crossRef = DailyEntryGoalsCrossRef(entry.dailyEntryId!!,goalid)
                database.dailyEntryDao().insertDailyEntryGoalCrossRef(crossRef)
            }
            this.requireActivity().runOnUiThread {
                adapter.updateEntry(entry)
            }
        }
    }

    override fun onEntryDeleted(entry: DailyEntry) {
        thread{
            database.dailyEntryDao().deleteDailyEntry(entry)
            database.dailyEntryDao().deleteCrossRefByEntry(entry.dailyEntryId!!)
            this.requireActivity().runOnUiThread {
                adapter.delete(entry)
            }
        }
    }

    override fun OnEntryAdded(newEntry: DailyEntry, goals:List<Long>) {
        thread {
            val insertId = database.dailyEntryDao().insertDailyEntry(newEntry)
            newEntry.dailyEntryId = insertId
            for(goal in goals){
                val goalid:Long = goal
                val crossRef = DailyEntryGoalsCrossRef(newEntry.dailyEntryId!!,goalid)
                database.dailyEntryDao().insertDailyEntryGoalCrossRef(crossRef)
            }
            this.requireActivity().runOnUiThread {
                adapter.addEntry(newEntry)
            }
        }
    }

    override fun onItemClick(entry: DailyEntry) {
        val intent = Intent(this.context,EntryDetailsActivity::class.java)
        intent.putExtra(AddEntryActivity.ENTRY_TO_EDIT,entry)
        var dailyentrywithgoals: List<DailyEntryWithGoal>
        thread{
            val goals = database.dailyEntryDao().getAllGoal()
            val goalsarray:ArrayList<Goal> = ArrayList(goals)
            intent.putParcelableArrayListExtra(AddEntryActivity.ALL_GOALS,goalsarray)
            dailyentrywithgoals = database.dailyEntryDao().getGoalsOfDailyEntry(entry.dailyEntryId!!)
            val actualgoalsarray:ArrayList<Goal> = ArrayList(dailyentrywithgoals[0].goals)
            intent.putParcelableArrayListExtra(AddEntryActivity.ALREADY_SELECTED_GOALS,actualgoalsarray)
            deleteOrUpdateContent.launch(intent)
        }
    }
}