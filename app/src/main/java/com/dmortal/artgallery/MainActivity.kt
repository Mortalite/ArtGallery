package com.dmortal.artgallery

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dmortal.artgallery.databinding.ActivityMainBinding
import com.dmortal.artgallery.ds.MainData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity :    AppCompatActivity(),
						FragmentCommunicator {

	private val TAG = this.javaClass.simpleName
	private lateinit var binding: ActivityMainBinding
	private var RESTORE_GALLERY_DATA: String = "GALLERY_DATA"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
//		mainData = MainData()
	}

	override fun onRestoreInstanceState(savedInstanceState: Bundle) {
		super.onRestoreInstanceState(savedInstanceState)

/*		mainData?.dataAdapter?.galleryData = Gson().fromJson(
			savedInstanceState.getString(RESTORE_GALLERY_DATA),
			object: TypeToken<MutableList<DataDTO>>(){}.type)
		mainData?.dataAdapter?.submitList(mainData?.dataAdapter?.galleryData)
		mainData?.dataAdapter?.notifyDataSetChanged()*/
	}

	override fun onStart() {
		super.onStart()
		openGalleryFragment()
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)

/*		outState.apply {
			outState.putString(RESTORE_GALLERY_DATA, Gson().toJson(mainData?.dataAdapter?.galleryData))
		}*/
	}

	override fun getMainData(): MainData? {
		return mainData
	}

	override fun isFirstCreate(): Boolean {
		return isFirstCreate
	}

	override fun setIsFirstCreate(value: Boolean) {
		isFirstCreate = value
	}

	override fun makeToast(message: String) {
		Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
	}

	override fun openGalleryFragment() {
		val galleryFragment = GalleryFragment.newInstance()
		val transaction = supportFragmentManager.beginTransaction()
		transaction.replace(R.id.container, galleryFragment)
		transaction.commit()
	}

	companion object {
		private var isFirstCreate: Boolean = true
		private var mainData: MainData = MainData()
	}

}