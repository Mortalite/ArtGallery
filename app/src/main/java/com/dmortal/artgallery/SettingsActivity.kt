package com.dmortal.artgallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmortal.artgallery.databinding.ActivitySettingsBinding

class SettingsActivity : 	AppCompatActivity(),
							SettingsCommunicator {

	private val TAG = this.javaClass.simpleName
	private lateinit var binding: ActivitySettingsBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivitySettingsBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}

	override fun onStart() {
		super.onStart()
		openSettingsFragment()
	}

	override fun openMainActivity() {
		startActivity(Intent(this, MainActivity::class.java))
	}

	override fun disableToolbarTitle() {
		supportActionBar?.setDisplayShowTitleEnabled(false)
	}

	private fun openSettingsFragment() {
		val settingsFragment = SettingsFragment.newInstance()
		val transaction = supportFragmentManager.beginTransaction()
		transaction.replace(R.id.activity_settings_container, settingsFragment)
		transaction.commit()
	}


}