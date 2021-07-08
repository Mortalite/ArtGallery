package com.dmortal.artgallery.retrofit

import com.dmortal.artgallery.ResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetImageService {

//    @GET("https://api.artic.edu/api/v1/artworks?page=1&limit=5&fields=id,title,artist_display,date_display,main_reference_number,image_id,iiif_url")
//    @GET("/api/v1/artworks?page={page}&limit=5&fields=id,image_id")
    @GET("/api/v1/artworks")
    fun getImagesFromPage(@Query("page") page: Int,
                          @Query("limit") limit: Int,
                          @Query("fields") fields: String): Call<ResponseDTO>

}