package com.dmortal.artgallery

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dmortal.artgallery.databinding.ActivityMainBinding

class MainActivity :    AppCompatActivity(),
						MainCommunicator {

	private val TAG = this.javaClass.simpleName
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}

	override fun onStart() {
		super.onStart()
		openGalleryFragment()
	}

	override fun makeToast(message: String) {
		Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
	}

	override fun openSettingsActivity() {
		startActivity(Intent(this, SettingsActivity::class.java))
	}

	override fun openGalleryFragment() {
		val galleryFragment = GalleryFragment.newInstance()
		val transaction = supportFragmentManager.beginTransaction()
		transaction.replace(R.id.activity_main_container, galleryFragment)
		transaction.commit()
	}

	override fun disableToolbarTitle() {
		supportActionBar?.setDisplayShowTitleEnabled(false)
	}

}