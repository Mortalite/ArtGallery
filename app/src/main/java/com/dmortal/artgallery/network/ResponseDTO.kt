
package com.dmortal.artgallery

import com.google.gson.annotations.SerializedName

class ResponseDTO(
	@SerializedName("pagination") var pagination: PaginationDTO,
	@SerializedName("data") var data: List<DataDTO>? = null
//	@SerializedName("info") var config: info,
//	@SerializedName("config") var config: Config,
)

data class PaginationDTO(
	@SerializedName("total") var total: Int,
	@SerializedName("limit") var limit: Int,
	@SerializedName("offset") var offset: Int,
	@SerializedName("total_pages") var totatPages: Int,
	@SerializedName("current_page") var currentPage: Int,
	@SerializedName("next_url") var nextUrl: String
)

data class DataDTO(
	@SerializedName("id") var id: Int,
	@SerializedName("image_id") var imageId: String
)

data class InfoDTO(
	@SerializedName("license_text") var licenseText: String,
	@SerializedName("license_links") var licenseLinks: List<String>,
	@SerializedName("version") var version: String
)

data class ConfigDTO(
	@SerializedName("iiif_url") var iiif_url: String,
	@SerializedName("website_url") var website_url: String
)