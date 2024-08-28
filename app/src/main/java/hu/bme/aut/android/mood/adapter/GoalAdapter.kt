package hu.bme.aut.android.mood.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.mood.R
import hu.bme.aut.android.mood.data.Goal
import hu.bme.aut.android.mood.databinding.GoalItemBinding

class GoalAdapter(private val listenerDelete:GoalClickListener) :
    RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    private val goals = mutableListOf<Goal>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GoalViewHolder(
        GoalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals.get(position)
        holder.binding.tvGoalName.text = goal.name
        holder.binding.tvGoalDesc.text = "Description:\n" + goal.description
        holder.binding.tvGoalDesc.visibility = View.INVISIBLE
        holder.binding.deletegoal.setOnClickListener{
            listenerDelete.onGoalDeleted(goal.goalId!!)
        }
        holder.binding.deletegoal.visibility = View.INVISIBLE
        holder.binding.ivGoalIcon.setImageResource(getImageResource(goal.category))
        holder.binding.tvGoalCategory.text = getCategory(goal.category)
        holder.itemView.setOnClickListener{
            if(holder.binding.tvGoalDesc.visibility == View.INVISIBLE){
                holder.binding.tvGoalDesc.visibility = View.VISIBLE;
                holder.binding.deletegoal.visibility=View.VISIBLE;
            }
            else {
                holder.binding.tvGoalDesc.visibility = View.INVISIBLE
                holder.binding.deletegoal.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount(): Int = goals.size

    interface GoalClickListener {
        fun onGoalDeleted(id:Long)
    }

    inner class GoalViewHolder(val binding: GoalItemBinding) : RecyclerView.ViewHolder(binding.root)

    @DrawableRes
    private fun getImageResource(category: Goal.GoalCategory):Int{
        return when(category) {
            Goal.GoalCategory.HEALTH -> R.drawable.ic_health
            Goal.GoalCategory.OTHER -> R.drawable.ic_other
            Goal.GoalCategory.STUDIES -> R.drawable.ic_studies
            Goal.GoalCategory.FINANCIAL -> R.drawable.ic_financial
            Goal.GoalCategory.LIFESTYLE -> R.drawable.ic_lifestyle
            Goal.GoalCategory.SELFCARE -> R.drawable.ic_selfcare
        }
    }

    private fun getCategory(category: Goal.GoalCategory):String{
        return when(category) {
            Goal.GoalCategory.HEALTH -> "Health"
            Goal.GoalCategory.OTHER -> "Other"
            Goal.GoalCategory.STUDIES -> "Studies"
            Goal.GoalCategory.FINANCIAL -> "Financial"
            Goal.GoalCategory.LIFESTYLE -> "Lifestyles"
            Goal.GoalCategory.SELFCARE -> "Selfcare"
        }
    }

    fun addGoal(goal: Goal) {
        goals.add(goal)
        notifyItemInserted(goals.size - 1)
    }

    fun update(shoppingItems: List<Goal>) {
        goals.clear()
        goals.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    fun delete(id:Long){
        val goalscpy =  mutableListOf<Goal>()
        goalscpy.addAll(goals)
        for(goal in goalscpy){
            if(goal.goalId==id){
                val indx = goals.indexOf(goal);
                goals.remove(goal)
                notifyItemRemoved(indx)
            }
        }
    }
}