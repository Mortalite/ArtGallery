package com.dmortal.artgallery.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.dmortal.artgallery.ds.Settings

class DBManager(context: Context) {

	private val TAG = this.javaClass.simpleName
	private var db: SQLiteDatabase

	init {
//		dropDatabase(context)
		val sqlHelper = SQLHelper(context)
		db = sqlHelper.writableDatabase
//		resetInstanseSettings()
	}


	fun dropDatabase(context: Context) {
		context.deleteDatabase(SQLHelper.DB_NAME)
	}

	fun close() {
		db.close()
	}

	fun getSettings(): Settings? {
		val cursor = db.query(
			SQLHelper.TABLE_SETTINGS,
			SQLHelper.projection,
			null,
			null,
			null,
			null,
			null)

		with(cursor) {
			try {
				cursor.moveToNext()
				val settings = Settings(
					getInt(getColumnIndex(SQLHelper.COLUMN_TOTAL_PAGES)),
					getInt(getColumnIndex(SQLHelper.COLUMN_GRID_SPAN)),
					getInt(getColumnIndex(SQLHelper.COLUMN_INIT_LIMIT)),
					getInt(getColumnIndex(SQLHelper.COLUMN_LOAD_LIMIT)),
					getString(getColumnIndex(SQLHelper.COLUMN_FIELDS)),
					getInt(getColumnIndex(SQLHelper.COLUMN_LOADING_STATUS)),
					getInt(getColumnIndex(SQLHelper.COLUMN_PREVIOUS_ITEM_COUNT)),
					getInt(getColumnIndex(SQLHelper.COLUMN_FIRST_PAGE)),
					getInt(getColumnIndex(SQLHelper.COLUMN_CURRENT_PAGE)),
					getInt(getColumnIndex(SQLHelper.COLUMN_FIRST_POSITION)),
					getInt(getColumnIndex(SQLHelper.COLUMN_LAST_POSITION)),
				)
				return settings
			}
			catch (exception: Exception) {
				Log.e(TAG, exception.message.toString())
			}
			cursor.close()
			return null
		}
	}

	fun saveSettings(settings: Settings?) {
		settings?.let {
			val content: ContentValues = ContentValues().apply {
				// Actual settings
				put(SQLHelper.COLUMN_TOTAL_PAGES, settings.totalPages)
				put(SQLHelper.COLUMN_GRID_SPAN, settings.gridSpan)
				put(SQLHelper.COLUMN_INIT_LIMIT, settings.initLimit)
				put(SQLHelper.COLUMN_LOAD_LIMIT, settings.loadLimit)
				put(SQLHelper.COLUMN_FIELDS, settings.fields)
				put(SQLHelper.COLUMN_LOADING_STATUS, settings.loadingStatus)
				put(SQLHelper.COLUMN_PREVIOUS_ITEM_COUNT, settings.previousItemCount)
				put(SQLHelper.COLUMN_FIRST_PAGE, settings.firstPage)
				put(SQLHelper.COLUMN_CURRENT_PAGE, settings.currentPage)
				put(SQLHelper.COLUMN_FIRST_POSITION, settings.firstPosition)
				put(SQLHelper.COLUMN_LAST_POSITION, settings.lastPosition)
			}
			db.update(SQLHelper.TABLE_SETTINGS, content, "id = '1'", null)
		}
	}

	fun saveInstanceSettings(settings: Settings?) {
		settings?.let {
			val content: ContentValues = ContentValues().apply {
				// Actual settings
				put(SQLHelper.COLUMN_LOADING_STATUS, it.loadingStatus)
				put(SQLHelper.COLUMN_PREVIOUS_ITEM_COUNT, it.previousItemCount)
				put(SQLHelper.COLUMN_FIRST_PAGE, it.firstPage)
				put(SQLHelper.COLUMN_CURRENT_PAGE, it.currentPage)
				put(SQLHelper.COLUMN_FIRST_POSITION, it.firstPosition)
				put(SQLHelper.COLUMN_LAST_POSITION, it.lastPosition)
			}
			db.update(SQLHelper.TABLE_SETTINGS, content, "id = '1'", null)
		}
	}

	fun resetInstanseSettings() {
		val content: ContentValues = ContentValues().apply {
			// Actual settings
			put(SQLHelper.COLUMN_LOADING_STATUS, 0)
			put(SQLHelper.COLUMN_PREVIOUS_ITEM_COUNT, -1)
			put(SQLHelper.COLUMN_FIRST_PAGE, 1)
			put(SQLHelper.COLUMN_CURRENT_PAGE, 0)
			put(SQLHelper.COLUMN_FIRST_POSITION, 0)
			put(SQLHelper.COLUMN_LAST_POSITION, 0)
		}
		db.update(SQLHelper.TABLE_SETTINGS, content, "id = '1'", null)
	}

}