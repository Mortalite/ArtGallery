package com.dmortal.artgallery.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "persistentSettings")
@TypeConverters(Converter::class)
data class PersistentSettingsEntity(
	@PrimaryKey(autoGenerate = true) val id: Int,
	@ColumnInfo(name = "grid_span") var gridSpan: Int,
	@ColumnInfo(name = "scale_type") var scaleType: SCALE_TYPE
)
{

	enum class SCALE_TYPE {
		fitXY,
		fitCenter
	}

}