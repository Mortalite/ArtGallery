package com.dmortal.artgallery.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dmortal.artgallery.ResponseDTO
import com.dmortal.artgallery.adapter.DataAdapter
import com.dmortal.artgallery.network.GetImageService
import com.dmortal.artgallery.network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataViewModel: ViewModel() {

	private val TAG = this.javaClass.simpleName
	var dataAdapter: DataAdapter? = null
	var service: GetImageService? = null
	var itemsCount: Int = 0

	init {
		dataAdapter = DataAdapter()
		service = RetrofitClientInstance.retrofitInstance?.create(GetImageService::class.java)
	}

	fun loadData(page: Int, limit: Int, fields: String) {
		val call = service?.getImagesFromPage(
			page,
			limit,
			fields
		)
		loadDataAsync(call)
	}

	private fun loadDataAsync(call: Call<ResponseDTO>?) {
		call?.enqueue(object : Callback<ResponseDTO> {

			override fun onResponse(
				call: Call<ResponseDTO>,
				response: Response<ResponseDTO>
			) {
				dataAdapter?.galleryData.let {
					galleryData ->
					response.body()?.data?.let {
						rspData ->
						galleryData?.addAll(rspData.filter { !it.imageId.isNullOrBlank() })
					}
					itemsCount += response.body()?.data?.size ?: 0
					dataAdapter?.submitList(galleryData)
					dataAdapter?.notifyDataSetChanged()
				}
			}

			override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
				Log.e(TAG, t.printStackTrace().toString())
			}

		})
	}

}