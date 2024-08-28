package hu.bme.aut.android.mood

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import hu.bme.aut.android.mood.data.DailyEntry
import hu.bme.aut.android.mood.data.Goal
import hu.bme.aut.android.mood.databinding.ActivityEntryDetailsBinding
class EntryDetailsActivity : AppCompatActivity() {

    companion object{
        const val EXIT = 11;
        const val EDIT = 5;
        const val DELETE=10;
        const val EDIT_BOOLEAN="edit"
        const val ENTRY_TO_DELETE = "deleteEntry"
    }

    private lateinit var binding: ActivityEntryDetailsBinding
    private lateinit var entry:DailyEntry
    private var goals = mutableMapOf<String,Long>()

    private val rotateOpen: Animation by lazy{AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy{AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy{AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy{AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim)}

    private var clk = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        entryBuilder()
        binding.dateDetails.text = entry.date
        binding.tvPositiveThingsDetails.text = entry.positiveThings
        binding.tvNegativeThings.text = entry.negativeThings
        binding.tvThoughts.text = entry.thoughts
        binding.imgmoodDetails.setImageResource(getResource(entry.mood))
        val imgUri = Uri.parse(entry.image)
        Glide.with(this)
            .load(imgUri)
            .into(binding.ivImage)
        binding.doneButton.setOnClickListener{
            val intent = Intent()
            setResult(EXIT,intent)
            finish()
        }
        var goalsString = ""
        val goalsnames = mutableListOf<String>()
        goalsnames.addAll(goals.keys)
        for(g in goalsnames){
            goalsString+= g + "\n"
        }
        if(goals.isNotEmpty())
            binding.tvGoalsList.text=goalsString
        binding.constraintlayout.setOnClickListener{
            if(clk){
                setVisibility(clk)
                setAnimation(clk)
                clk = !clk
            }
        }
        binding.fabOptions.setOnClickListener{
            onOptionsButtonClicked()
        }
        binding.fabEdit.setOnClickListener{
            val intent = Intent()
            intent.putExtras(getIntent())
            intent.putExtra(EDIT_BOOLEAN,true)
            setResult(EDIT,intent)
            finish()
        }
        binding.fabDelete.setOnClickListener{
            val intent = Intent()
            intent.putExtra(ENTRY_TO_DELETE,entry)
            setResult(DELETE,intent)
            finish()
        }
    }

    private fun entryBuilder(){
        val actualgoalsarray:ArrayList<Goal> = intent.getParcelableArrayListExtra(AddEntryActivity.ALREADY_SELECTED_GOALS)!!
        for(goal in actualgoalsarray){
            goals[goal.name] = goal.goalId!!
        }
        entry = intent.getParcelableExtra(AddEntryActivity.ENTRY_TO_EDIT)!!
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
            DailyEntry.Mood.SAD ->R.drawable.ic_sadradio
            DailyEntry.Mood.TERRIBLE ->R.drawable.ic_terribleradio
        }
    }

    private fun onOptionsButtonClicked(){
        setVisibility(clk)
        setAnimation(clk)
        clk = !clk
    }

    private fun setVisibility(clk: Boolean){
        if(!clk){
            binding.fabEdit.visibility = View.VISIBLE
            binding.fabDelete.visibility = View.VISIBLE
        }
        else{
            binding.fabEdit.visibility = View.INVISIBLE
            binding.fabDelete.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clk:Boolean){
        if(!clk){
            binding.fabEdit.startAnimation(fromBottom)
            binding.fabDelete.startAnimation(fromBottom)
            binding.fabOptions.startAnimation(rotateOpen)
        }
        else {
            binding.fabEdit.startAnimation(toBottom)
            binding.fabDelete.startAnimation(toBottom)
            binding.fabOptions.startAnimation(rotateClose)
        }
    }
}