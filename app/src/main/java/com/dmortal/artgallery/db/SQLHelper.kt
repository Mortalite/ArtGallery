package com.dmortal.artgallery.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dmortal.artgallery.ds.Settings

class SQLHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

	companion object {
		const val DB_VERSION = 1
		const val DB_NAME = "Quiz.db"

		const val COLUMN_ID = "ID"

		const val TABLE_SETTINGS: String = "SETTINGS"

		const val COLUMN_TOTAL_PAGES: String = "TOTAL_PAGES"
		const val COLUMN_GRID_SPAN: String = "GRID_SPAN"
		const val COLUMN_INIT_LIMIT: String = "INIT_LIMIT"
		const val COLUMN_LOAD_LIMIT: String = "LOAD_LIMIT"
		const val COLUMN_FIELDS: String = "FIELDS"
		const val COLUMN_LOADING_STATUS: String = "LOADING_STATUS"
		const val COLUMN_PREVIOUS_ITEM_COUNT: String = "PREVIOUS_TIME_COUNT"
		const val COLUMN_FIRST_PAGE: String = "FIRST_PAGE"
		const val COLUMN_CURRENT_PAGE: String = "CURRENT_PAGE"
		const val COLUMN_FIRST_POSITION: String = "FIRST_POSITION"
		const val COLUMN_LAST_POSITION: String = "LAST_POSITION"

		val projection = arrayOf(
			COLUMN_TOTAL_PAGES,
			COLUMN_GRID_SPAN,
			COLUMN_INIT_LIMIT,
			COLUMN_LOAD_LIMIT,
			COLUMN_FIELDS,
			COLUMN_LOADING_STATUS,
			COLUMN_PREVIOUS_ITEM_COUNT,
			COLUMN_FIRST_PAGE,
			COLUMN_CURRENT_PAGE,
			COLUMN_FIRST_POSITION,
			COLUMN_LAST_POSITION
		)

	}

	override fun onCreate(db: SQLiteDatabase?) {
		if (db != null)
			updateDatabase(db, 0, DB_VERSION)
	}

	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
		if (db != null)
			updateDatabase(db, oldVersion, newVersion)
	}

	private fun updateDatabase(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
		db.execSQL(
			"CREATE TABLE IF NOT EXISTS $TABLE_SETTINGS (" +
					"$COLUMN_ID INTEGER PRIMARY KEY," +
					// Actual settings
					"$COLUMN_TOTAL_PAGES INTEGER DEFAULT ${Settings.defaultTotalPages}," +
					"$COLUMN_GRID_SPAN INTEGER DEFAULT ${Settings.defaultGridSpan}," +
					"$COLUMN_INIT_LIMIT INTEGER DEFAULT ${Settings.defaultInitLimit}," +
					"$COLUMN_LOAD_LIMIT INTEGER DEFAULT ${Settings.defaultLoadLimit}," +
					"$COLUMN_FIELDS TEXT DEFAULT ${Settings.defaultFields}," +
					"$COLUMN_LOADING_STATUS INTEGER DEFAULT ${Settings.defaultLoadingStatus}," +
					"$COLUMN_PREVIOUS_ITEM_COUNT INTEGER DEFAULT ${Settings.defaultPreviousItemCount}," +
					"$COLUMN_FIRST_PAGE INTEGER DEFAULT ${Settings.defaultFirstPage}," +
					"$COLUMN_CURRENT_PAGE INTEGER DEFAULT ${Settings.defaultCurrentPage}," +
					"$COLUMN_FIRST_POSITION INTEGER DEFAULT ${Settings.defaultFirstPosition}," +
					"$COLUMN_LAST_POSITION INTEGER DEFAULT ${Settings.defaultLastPosition});")

		val content = ContentValues().apply {
			// Actual settings
			put(COLUMN_TOTAL_PAGES, Settings.defaultTotalPages)
			put(COLUMN_GRID_SPAN, Settings.defaultGridSpan)
			put(COLUMN_INIT_LIMIT, Settings.defaultInitLimit)
			put(COLUMN_LOAD_LIMIT, Settings.defaultLoadLimit)
			put(COLUMN_FIELDS, Settings.defaultFields)
			put(COLUMN_LOADING_STATUS, Settings.defaultLoadingStatus)
			put(COLUMN_PREVIOUS_ITEM_COUNT, Settings.defaultPreviousItemCount)
			put(COLUMN_FIRST_PAGE, Settings.defaultFirstPage)
			put(COLUMN_CURRENT_PAGE, Settings.defaultCurrentPage)
			put(COLUMN_FIRST_POSITION, Settings.defaultFirstPosition)
			put(COLUMN_LAST_POSITION, Settings.defaultLastPosition)
		}

		db.insert(TABLE_SETTINGS, null, content)
	}
}