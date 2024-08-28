package hu.bme.aut.android.mood.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.mood.adapter.GoalAdapter
import hu.bme.aut.android.mood.data.DailyEntryDatabase
import hu.bme.aut.android.mood.data.Goal
import hu.bme.aut.android.mood.databinding.FragmentGoalsBinding
import kotlin.concurrent.thread

class GoalsFragment : Fragment(), GoalAdapter.GoalClickListener, NewGoalDialogFragment.NewGoalDialogListener {

    private lateinit var binding: FragmentGoalsBinding

    private lateinit var database:DailyEntryDatabase
    private lateinit var adapter: GoalAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGoalsBinding.inflate(inflater, container, false)

        database = DailyEntryDatabase.getDatabase(this.requireContext().applicationContext)
        initRecyclerView()
        binding.fab.setOnClickListener{
            NewGoalDialogFragment(this).show(
                requireActivity().supportFragmentManager,
                NewGoalDialogFragment.TAG
            )
        }
        return binding.root;
    }

    private fun initRecyclerView() {
        adapter = GoalAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val goals = database.dailyEntryDao().getAllGoal()
            requireActivity().runOnUiThread {
                adapter.update(goals)
            }
        }
    }

    override fun onGoalCreated(newGoal: Goal) {
        thread {
            val insertId = database.dailyEntryDao().insertGoal(newGoal)
            newGoal.goalId = insertId
            requireActivity().runOnUiThread {
                adapter.addGoal(newGoal)
            }
        }
    }

    override fun onGoalDeleted(id:Long) {
        thread{
            val goalToDelete = database.dailyEntryDao().getGoal(id)
            database.dailyEntryDao().deleteGoal(goalToDelete)
            database.dailyEntryDao().deleteCrossRefByGoal(goalToDelete.goalId!!)
            this.requireActivity().runOnUiThread {
                adapter.delete(id)
            }
        }
    }

}