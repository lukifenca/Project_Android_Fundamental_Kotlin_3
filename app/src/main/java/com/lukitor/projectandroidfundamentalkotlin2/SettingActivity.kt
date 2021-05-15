package com.lukitor.projectandroidfundamentalkotlin2

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lukitor.projectandroidfundamentalkotlin2.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    companion object{private const val RESULT_CODE=102}
    private lateinit var mAlarmPrefences: AlarmPreferences
    private lateinit var alarmReceiver: AlarmReceiver
    var statusAlarm:Boolean=false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Glide.with(this).load(R.drawable.notification).into(binding.imageView4)
        binding.imgback2.setOnClickListener{ view ->finish()}

        mAlarmPrefences = AlarmPreferences(this)
        getAlarm()
        binding.switchNotif.isChecked=statusAlarm
        binding.switchNotif.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            setAlarm(isChecked)
        })
        var actionBar = getSupportActionBar()
        if (actionBar != null) {actionBar.setDisplayHomeAsUpEnabled(true)}
        alarmReceiver= AlarmReceiver()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        println(item.itemId.toString())
        println(android.R.id.home.toString())
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent()
                intent.putExtra("statusAlarm", statusAlarm)
                setResult(RESULT_CODE,intent)
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
    private fun setAlarm(boolean:Boolean) {
        val alarmPreferences = AlarmPreferences(this)
        statusAlarm = boolean
        alarmPreferences.setStatus(boolean)
        if(boolean) alarmReceiver.setRepeatingAlarm(this,AlarmReceiver.TYPE_REPEATING,"09:00","Hey, It's already 09:00 AM.")
        else alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)

    }
    fun getAlarm(){
        statusAlarm=mAlarmPrefences.getStatus()
        if(statusAlarm.toString().isEmpty())statusAlarm=false
    }
}