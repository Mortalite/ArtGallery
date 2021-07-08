package com.dmortal.artgallery.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dmortal.artgallery.DataDTO
import com.dmortal.artgallery.databinding.DataItemBinding
import java.lang.Exception

class DataViewHolder(
	private val binding: DataItemBinding
): RecyclerView.ViewHolder(binding.root) {

	private val TAG = this.javaClass.simpleName
	// image quality : 200, 400, 600, 843, 1686
	private val quality = "200"

	fun bind(dataDTO: DataDTO) {

		with(binding) {
			try {
				Glide.with(imageView.context)
					.load("https://www.artic.edu/iiif/2/${dataDTO.imageId}/full/${quality},/0/default.jpg")
					.into(imageView)
			}
			catch (exception: Exception) {
				Log.e(TAG, exception.printStackTrace().toString())
			}
		}
	}

}