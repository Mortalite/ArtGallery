package com.dmortal.artgallery.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1,2) {
	override fun migrate(database: SupportSQLiteDatabase) {
		database.execSQL("ALTER TABLE persistentSettings ADD COLUMN scale_type TEXT DEFAULT 'fitXY' NOT NULL")
	}
}

@Database(
	entities = arrayOf(PersistentSettingsEntity::class),
	version = 2,
)
abstract class PersistentDatabase: RoomDatabase() {

	abstract fun persistentSettingsDao(): PersistentSettingsDao

	companion object {
		@Volatile
		private var databaseInstance: PersistentDatabase? = null

		fun getDatabase(context: Context): PersistentDatabase {
			databaseInstance?.let {
				return it
			}
			val instance = Room.databaseBuilder(
				context.applicationContext,
				PersistentDatabase::class.java,
				"ArtGallery.db")
				.addMigrations(MIGRATION_1_2)
				.createFromAsset("ArtGallery_V2.db")
				.build()
			databaseInstance = instance
			return instance
		}

	}


}