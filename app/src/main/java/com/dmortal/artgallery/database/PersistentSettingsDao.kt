package com.dmortal.artgallery.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersistentSettingsDao {

//	@Query("UPDATE persistentSettings SET grid_span=:gridSpan WHERE id=1")
//	fun updateGridSpan(gridSpan: Int)

//	@Query("UPDATE persistentSettings SET scale_type=:scaleType WHERE id=1")
//	fun updateScaleType(scaleType: PersistentSettingsEntity.SCALE_TYPE)

	@Update
	fun updatePersistentSettings(persistentSettings: PersistentSettingsEntity)

	@Query("SELECT * FROM persistentSettings")
	fun getPersistentSettings(): LiveData<PersistentSettingsEntity>

}