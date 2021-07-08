package com.dmortal.artgallery.ds

import com.dmortal.artgallery.adapter.DataAdapter
import com.dmortal.artgallery.retrofit.GetImageService
import com.dmortal.artgallery.retrofit.RetrofitClientInstance

data class MainData @JvmOverloads constructor(
	val service: GetImageService? = RetrofitClientInstance.retrofitInstance?.create(GetImageService::class.java),
	var dataAdapter: DataAdapter = DataAdapter()
)
