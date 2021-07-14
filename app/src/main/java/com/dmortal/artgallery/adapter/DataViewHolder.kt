package com.dmortal.artgallery.adapter

import android.content.res.Configuration
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dmortal.artgallery.DataDTO
import com.dmortal.artgallery.database.PersistentSettingsEntity
import com.dmortal.artgallery.databinding.DataItemBinding
import com.dmortal.artgallery.viewmodel.PersistentSettingsViewModel
import java.lang.Exception

class DataViewHolder(
	private val binding: DataItemBinding): RecyclerView.ViewHolder(binding.root) {

	private val TAG = this.javaClass.simpleName

	fun bind(dataDTO: DataDTO, scaleType: PersistentSettingsEntity.SCALE_TYPE, quality: String) {

		with(binding) {
			try {
				dataItemImageView.scaleType = getScaleType(scaleType)
				Glide.with(dataItemImageView.context)
					.load("https://www.artic.edu/iiif/2/${dataDTO.imageId}/full/${quality},/0/default.jpg")
					.into(dataItemImageView)
			}
			catch (exception: Exception) {
				Log.e(TAG, exception.printStackTrace().toString())
			}
		}
	}

	fun getScaleType(scaleType: PersistentSettingsEntity.SCALE_TYPE): ImageView.ScaleType = when {
		(scaleType == PersistentSettingsEntity.SCALE_TYPE.fitXY) -> ImageView.ScaleType.FIT_XY
		else -> ImageView.ScaleType.FIT_CENTER
	}

}