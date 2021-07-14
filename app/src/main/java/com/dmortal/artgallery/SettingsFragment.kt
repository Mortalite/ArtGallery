package com.dmortal.artgallery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dmortal.artgallery.databinding.FragmentSettingsBinding
import com.dmortal.artgallery.viewmodel.InstanceSettingsViewModel
import com.dmortal.artgallery.viewmodel.PersistentSettingsViewModel

class SettingsFragment : Fragment() {

	private val TAG = this.javaClass.simpleName
	private var _binding: FragmentSettingsBinding? = null
	private val binding get() = _binding!!

	private var communicator: SettingsCommunicator? = null
	private var instanceSettingsViewModel: InstanceSettingsViewModel? = null
	private var persistentSettingsViewModel: PersistentSettingsViewModel? = null

	override fun onAttach(context: Context) {
		super.onAttach(context)

		communicator = activity as SettingsCommunicator
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSettingsBinding.inflate(inflater, container, false)
		initViewModel()
		setPreferenceFragment()
		toolbarSetup()

		binding.settingsToolbarBackArrow.setOnClickListener {
			communicator?.openMainActivity()
		}
		return binding.root
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

	private fun initViewModel() {
		instanceSettingsViewModel = ViewModelProvider(requireActivity()).get(InstanceSettingsViewModel::class.java)
		persistentSettingsViewModel = ViewModelProvider(requireActivity()).get(PersistentSettingsViewModel::class.java)
	}

	private fun setPreferenceFragment() {
		val preferenceFragment = PreferenceFragment.newInstance()
		val transaction = activity?.supportFragmentManager?.beginTransaction()
		transaction?.replace(R.id.fragment_settings_container, preferenceFragment)
		transaction?.commit()
	}

	private fun toolbarSetup() {
		communicator?.disableToolbarTitle()
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