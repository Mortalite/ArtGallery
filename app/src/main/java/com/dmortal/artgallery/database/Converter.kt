package com.dmortal.artgallery.database

import androidx.room.TypeConverter

class Converter {

	@TypeConverter
	fun fromScaleType(value: PersistentSettingsEntity.SCALE_TYPE?): String? {
		return value?.name
	}

	@TypeConverter
	fun toScaleType(value: String?): PersistentSettingsEntity.SCALE_TYPE? {
		return value?.let { PersistentSettingsEntity.SCALE_TYPE.valueOf(it) }
	}

}