package com.dmortal.artgallery.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dmortal.artgallery.database.PersistentDatabase
import com.dmortal.artgallery.database.PersistentSettingsEntity

class PersistentSettingsViewModel(application: Application): AndroidViewModel(application) {

	private var databaseInstance: PersistentDatabase? = null
	private var liveDataSettings: LiveData<PersistentSettingsEntity>? = null

	companion object DEFAULT {
		var defaultGridSpan: Int = 3
		var defaultScaleType: PersistentSettingsEntity.SCALE_TYPE =
			PersistentSettingsEntity.SCALE_TYPE.fitXY
	}

	init {
		databaseInstance = PersistentDatabase.getDatabase(application)
		liveDataSettings = databaseInstance?.persistentSettingsDao()?.getPersistentSettings()
	}

	fun update(persistentSettings: LiveData<PersistentSettingsEntity>?) {
		persistentSettings?.value?.let {
			databaseInstance
				?.persistentSettingsDao()
				?.updatePersistentSettings(it)
		}
	}

	fun updateGridSpan(gridSpan: Int?) {
		liveDataSettings?.value?.gridSpan = gridSpan ?: defaultGridSpan
	}

	fun getGridSpan(): Int {
		return liveDataSettings?.value?.gridSpan ?: defaultGridSpan
	}

	fun updateScaleType(scaleType: PersistentSettingsEntity.SCALE_TYPE?) {
		liveDataSettings?.value?.scaleType = scaleType ?: defaultScaleType
	}

	fun updateScaleType(scaleType: String?) {
		liveDataSettings?.value?.scaleType =
			PersistentSettingsEntity.SCALE_TYPE.valueOf(
				scaleType ?: defaultScaleType.name)
	}

	fun getScaleType(): PersistentSettingsEntity.SCALE_TYPE {
		return liveDataSettings?.value?.scaleType ?: defaultScaleType
	}

	fun getPersistentSettings(): LiveData<PersistentSettingsEntity>? {
		return liveDataSettings
	}

}