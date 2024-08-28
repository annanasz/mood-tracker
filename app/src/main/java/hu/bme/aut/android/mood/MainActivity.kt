package hu.bme.aut.android.mood

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import hu.bme.aut.android.mood.adapter.MainPagerAdapter
import hu.bme.aut.android.mood.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        setTheme(R.style.Theme_MOOd)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        binding.viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> binding.bottomNavigationView.menu.findItem(R.id.entries_page).isChecked = true
                    1 -> binding.bottomNavigationView.menu.findItem(R.id.calendar_page).isChecked = true
                    2 -> binding.bottomNavigationView.menu.findItem(R.id.charts_page).isChecked = true
                    3 -> binding.bottomNavigationView.menu.findItem(R.id.goals_page).isChecked = true
                }
            }
        })
        binding.bottomNavigationView.setOnItemSelectedListener{ item ->
           when(item.itemId){
                R.id.entries_page -> {
                    binding.viewPager.currentItem = 0
                    true
                }
                R.id.calendar_page->{
                    binding.viewPager.currentItem = 1
                    true
                }
                R.id.charts_page -> {
                    binding.viewPager.currentItem = 2
                    true
                }
                R.id.goals_page -> {
                    binding.viewPager.currentItem = 3
                    true
                }
                else -> false
            }
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,Array(1){Manifest.permission.READ_EXTERNAL_STORAGE},121)
        }
        startNotification()
    }

    private fun startNotification(){
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY,20)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)

        val intent = Intent(applicationContext,NotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(applicationContext,100,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val am:AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_HALF_DAY,pendingIntent)
    }


}