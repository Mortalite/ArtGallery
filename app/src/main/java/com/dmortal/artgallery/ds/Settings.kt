package com.dmortal.artgallery.ds

data class Settings @JvmOverloads constructor(
	var totalPages: Int = defaultTotalPages,
	var gridSpan: Int = defaultGridSpan,
	var initLimit: Int = defaultInitLimit,
	var loadLimit: Int = defaultLoadLimit,
	var fields: String = defaultFields,
	var loadingStatus: Int = defaultLoadingStatus,
	var previousItemCount: Int = defaultPreviousItemCount,
	var firstPage: Int = defaultFirstPage,
	var currentPage: Int = defaultCurrentPage,
	var firstPosition: Int = defaultFirstPosition,
	var lastPosition: Int = defaultLastPosition
) {
	companion object DEFAULT {
		val defaultTotalPages: Int = 1
		var defaultGridSpan: Int = 3
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


	fun deepCopy(other: Settings) {
		this.totalPages = other.totalPages
		this.gridSpan = other.gridSpan
		this.initLimit = other.initLimit
		this.loadLimit = other.loadLimit
		this.fields = other.fields
		this.loadingStatus = other.loadingStatus
		this.previousItemCount = other.previousItemCount
		this.firstPage = other.firstPage
		this.currentPage = other.currentPage
		this.firstPosition = other.firstPosition
		this.lastPosition = other.lastPosition
	}

}
