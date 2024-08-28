package hu.bme.aut.android.mood.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.mood.R
import hu.bme.aut.android.mood.data.Goal
import hu.bme.aut.android.mood.databinding.DialogNewGoalBinding

class NewGoalDialogFragment(private var listener:NewGoalDialogListener) : DialogFragment() {
    interface NewGoalDialogListener {
        fun onGoalCreated(newGoal: Goal)
    }

    private lateinit var binding: DialogNewGoalBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewGoalBinding.inflate(LayoutInflater.from(context))
        binding.spGoalCategory.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.goal_category_items)
        )

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_goal)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { _, _ ->
                if (isValid()) {
                    listener.onGoalCreated(getGoal())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    companion object {
        const val TAG = "NewGoalDialogFragment"
    }
    private fun isValid() = binding.etGoalName.text.isNotEmpty()
    private fun getGoal() = Goal(
        name = binding.etGoalName.text.toString(),
        description = binding.etGoalDescription.text.toString(),
        category = Goal.GoalCategory.getByOrdinal(binding.spGoalCategory.selectedItemPosition)
            ?: Goal.GoalCategory.HEALTH,
    )
}