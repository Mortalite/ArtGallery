package com.dmortal.artgallery

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class SettingsFragment : PreferenceFragmentCompat() {

	private val TAG = this.javaClass.simpleName
	private var communicator: FragmentCommunicator? = null
	private var SHARED_PREFERENCES = "SHARED_PREFERENCES"
	override fun onAttach(context: Context) {
		super.onAttach(context)

		communicator = activity as FragmentCommunicator
	}
	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.root_preferences, rootKey)
		PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
			if (key == "scale_mode")
				Log.e(TAG, sharedPreferences?.getString("scale_mode", "NOT SET") ?: "NOT SET")
		}
	}


	override fun onStop() {
		super.onStop()

//		val string = PreferenceManager.getDefaultSharedPreferences(activity).getString("scaleMode")
//		val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
//		val scaleMode = sharedPreferences?.getString("scale_mode", "NOT SET")
//		Log.e(TAG, scaleMode.toString())

	}

	companion object {

		@JvmStatic
		fun newInstance() =
			SettingsFragment().apply {
				arguments = Bundle().apply {
				}
			}
	}

}