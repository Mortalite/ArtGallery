package com.dmortal.artgallery

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.dmortal.artgallery.viewmodel.InstanceSettingsViewModel
import com.dmortal.artgallery.viewmodel.PersistentSettingsViewModel

class PreferenceFragment: PreferenceFragmentCompat()  {

	private val TAG = this.javaClass.simpleName
	private var instanceSettingsViewModel: InstanceSettingsViewModel? = null
	private var persistentSettingsViewModel: PersistentSettingsViewModel? = null

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		initViewModel()
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.root_preferences, rootKey)
		PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
			if (key == "scale_mode") {
				persistentSettingsViewModel
					?.updateScaleType(sharedPreferences
						?.getString("scale_mode", PersistentSettingsViewModel.defaultScaleType.name))
				persistentSettingsViewModel?.update(persistentSettingsViewModel?.getPersistentSettings())
			}
			Log.e(TAG, "key = ${key}")
			Log.e(TAG, sharedPreferences?.getString("scale_mode", "NOT SET") ?: "NOT SET")
			Log.e(TAG, "${persistentSettingsViewModel?.getScaleType()}")
		}
	}

	fun initViewModel() {
		instanceSettingsViewModel = ViewModelProvider(requireActivity()).get(
			InstanceSettingsViewModel::class.java)
		persistentSettingsViewModel = ViewModelProvider(requireActivity()).get(
			PersistentSettingsViewModel::class.java)
	}

	companion object {

		@JvmStatic
		fun newInstance() =
			PreferenceFragment().apply {
				arguments = Bundle().apply {
				}
			}
	}

}