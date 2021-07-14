package com.dmortal.artgallery.network

import com.dmortal.artgallery.ResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetImageService {

    @GET("/api/v1/artworks")
    fun getImagesFromPage(@Query("page") page: Int,
                          @Query("limit") limit: Int,
                          @Query("fields") fields: String): Call<ResponseDTO>

}