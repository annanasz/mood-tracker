package hu.bme.aut.android.mood

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import hu.bme.aut.android.mood.data.DailyEntry
import hu.bme.aut.android.mood.data.Goal
import hu.bme.aut.android.mood.databinding.ActivityAddEntryBinding
import java.util.*
import kotlin.collections.ArrayList

class AddEntryActivity : AppCompatActivity() {

    companion object{
        const val SAVE = 1
        const val EDIT = 5
        const val CANCEL = 2
        const val ENTRY_TO_EDIT = "editEntry"
        const val NEW_ENTRY = "newEntry"
        const val ALL_GOALS = "goals"
        const val ALREADY_SELECTED_GOALS = "actualGoals"
        const val SELECTED_GOALS = "actual"
    }

    interface AddEntryListener{
        fun OnEntryAdded(newEntry:DailyEntry, goals:List<Long>)
    }

    private lateinit var binding: ActivityAddEntryBinding
    private var editedId:Long = -1
    private var edit = false
    private var goals = mutableMapOf<Int,Goal>()
    private var checkBoxes = mutableListOf<CheckBox>()
    private var img = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.saveButton.setOnClickListener{
            saveButtonClick()
        }
        binding.cancelButton.setOnClickListener {
            cancelButtonClick()
        }
        binding.radiogroup2.clearCheck()
        binding.radiogroup1.check(binding.radioAwesome.id)
        binding.radiogroup1.setOnCheckedChangeListener(listener1)
        binding.radiogroup2.setOnCheckedChangeListener(listener2)
        binding.addImage.setOnClickListener {
            selectImage()
        }
        initGoals()
        initForEdit()
    }

    private var listen2 = true
    private var listen1 = true

    private val listener1 = RadioGroup.OnCheckedChangeListener{
         _, checkedId ->
            if(checkedId!=-1 && listen1){
                listen2 = false
                binding.radiogroup2.clearCheck()
                listen2 = true
            }
        }

    private val listener2 = RadioGroup.OnCheckedChangeListener{
            _, checkedId ->
        if(checkedId!=-1 && listen2){
            listen1=false
            binding.radiogroup1.clearCheck()
            listen1=true
            binding.radiogroup1.setOnCheckedChangeListener(listener1)
        }
    }

    private fun getDateFrom(picker: DatePicker): String {
        return String.format(
            Locale.getDefault(), "%04d.%02d.%02d.",
            picker.year, picker.month + 1, picker.dayOfMonth
        )
    }

    private fun getMood(): DailyEntry.Mood{
        val id1 = binding.radiogroup1.checkedRadioButtonId
        val id2 = binding.radiogroup2.checkedRadioButtonId
        return when(id1)
        {
            binding.radioAwesome.id ->  DailyEntry.Mood.AWESOME
            binding.radioHappy.id -> DailyEntry.Mood.HAPPY
            binding.radioGood.id -> DailyEntry.Mood.GOOD
            binding.radioNeutral.id -> DailyEntry.Mood.NEUTRAL
            else -> {
                when (id2) {
                    binding.radioMoody.id -> DailyEntry.Mood.MOODY
                    binding.radioSad.id -> DailyEntry.Mood.SAD
                    binding.radioTerrible.id -> DailyEntry.Mood.TERRIBLE
                    binding.radioSick.id -> DailyEntry.Mood.SICK
                    else -> DailyEntry.Mood.AWESOME
                }
            }
        }
    }

    private fun saveButtonClick(){
        val selectedmood:DailyEntry.Mood = getMood()
        val intent = Intent()
        val date = getDateFrom(binding.dpDate)
        val positive = binding.etPositiveThings.text.toString()
        val negative = binding.etNegativeThings.text.toString()
        val thoughts = binding.etThoughts.text.toString()
        val entry = DailyEntry(null, selectedmood,date,positive,negative,thoughts,img)
        intent.putExtra(NEW_ENTRY,entry)
        val actualgoals = mutableListOf<Goal>()
        for(cb in checkBoxes)
        {
            if(cb.isChecked)
            {
                val goal = goals[cb.id]
                actualgoals.add(goal!!)
            }
        }
        val actualgoalsarray:ArrayList<Goal> = ArrayList(actualgoals)
        intent.putParcelableArrayListExtra(SELECTED_GOALS,actualgoalsarray)
        if(!edit)
             setResult(SAVE,intent)
        else{
            entry.dailyEntryId = editedId
            setResult(EDIT,intent)
        }
        finish()
    }

    private fun cancelButtonClick(){
        val intent = Intent()
        setResult(CANCEL,intent)
        finish()
    }

    private fun initForEdit(){
        edit = intent.getBooleanExtra(EntryDetailsActivity.EDIT_BOOLEAN,false)
        if(edit){
            val entry:DailyEntry = intent.getParcelableExtra(ENTRY_TO_EDIT)!!
            editedId=entry.dailyEntryId!!
            val date = entry.date
            val dates = date.split(".")
            binding.dpDate.updateDate(dates[0].toInt(), dates[1].toInt()-1, dates[2].toInt())
            val mood = entry.mood
            val positive = entry.positiveThings
            val negative = entry.negativeThings
            binding.etNegativeThings.setText(negative,TextView.BufferType.EDITABLE)
            binding.etPositiveThings.setText(positive,TextView.BufferType.EDITABLE)
            var radioId1 = -1
            var radioId2 = -1
            when(mood){
                DailyEntry.Mood.AWESOME -> radioId1 = binding.radioAwesome.id
                DailyEntry.Mood.HAPPY -> radioId1 = binding.radioHappy.id
                DailyEntry.Mood.GOOD -> radioId1 = binding.radioGood.id
                DailyEntry.Mood.NEUTRAL -> radioId1 = binding.radioNeutral.id
                DailyEntry.Mood.MOODY -> radioId2 = binding.radioMoody.id
                DailyEntry.Mood.SICK -> radioId2 = binding.radioSick.id
                DailyEntry.Mood.SAD ->radioId2 = binding.radioSad.id
                DailyEntry.Mood.TERRIBLE ->radioId2 = binding.radioTerrible.id
            }
            if(radioId1!=-1){
                binding.radiogroup1.check(radioId1)
            }
            else
                binding.radiogroup2.check(radioId2)
            val actualgoalsarray:ArrayList<Goal> = intent.getParcelableArrayListExtra(ALREADY_SELECTED_GOALS)!!
            for(goal in actualgoalsarray){
                val keys = goals.filterValues { it == goal }.keys
                val cbid:Int = keys.first()
                for(cb in checkBoxes){
                    if(cb.id == cbid)
                        cb.isChecked=true
                }
            }
            val imgUri = Uri.parse(entry.image)
            Glide.with(this)
                .load(imgUri)
                .into(binding.IVPreviewImage)
        }
    }

    private fun initGoals(){
        val goalsarray:ArrayList<Goal> = intent.getParcelableArrayListExtra(ALL_GOALS)!!
        for(goal in goalsarray){
            val cb = CheckBox(this.applicationContext)
            cb.id = goalsarray.indexOf(goal)
            cb.text = goal.name
            binding.llCheckboxes.addView(cb)
            checkBoxes.add(cb)
            goals[cb.id] = goal
        }
        if(goalsarray.size == 0)
            binding.nogoals.visibility = View.VISIBLE
        else
            binding.nogoals.visibility=View.INVISIBLE
    }

    private val getSelectedImage = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == RESULT_OK && it.data!=null){
            val selectedImageUri:Uri = it.data?.data!!
            binding.IVPreviewImage.setImageURI(selectedImageUri)
            img = selectedImageUri.toString()
        }
    }

    private fun selectImage()
    {
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        getSelectedImage.launch(i)
    }

}