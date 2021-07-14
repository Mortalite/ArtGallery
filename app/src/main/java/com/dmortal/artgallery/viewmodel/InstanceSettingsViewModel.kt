package com.dmortal.artgallery.viewmodel

import androidx.lifecycle.ViewModel

class InstanceSettingsViewModel: ViewModel() {

	companion object DEFAULT {
		var defaultIsFirstCreate: Boolean = true
		val defaultTotalPages: Int = 1
		var defaultInitLimit: Int = 20
		var defaultLoadLimit: Int = 10
		var defaultFields: String = "id,image_id"
		var defaultLoadingStatus: Int = 0
		var defaultPreviousItemCount: Int = -1
		var defaultFirstPage: Int = 1
		var defaultCurrentPage: Int = 0
		var defaultFirstPosition: Int = 0
		var defaultLastPosition: Int = 0
	}

	var isFirstCreate: Boolean = defaultIsFirstCreate
	var totalPages: Int = defaultTotalPages
	var initLimit: Int = defaultInitLimit
	var loadLimit: Int = defaultLoadLimit
	var fields: String = defaultFields
	var loadingStatus: Int = defaultLoadingStatus
	var previousItemCount: Int = defaultPreviousItemCount
	var firstPage: Int = defaultFirstPage
	var currentPage: Int = defaultCurrentPage
	var firstCompletelyVisible: Int = defaultFirstPosition
	var lastCompletelyVisible: Int = defaultLastPosition

}