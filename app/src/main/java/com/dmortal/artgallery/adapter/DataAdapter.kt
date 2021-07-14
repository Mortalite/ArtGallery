package com.dmortal.artgallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmortal.artgallery.DataDTO
import com.dmortal.artgallery.database.PersistentSettingsEntity
import com.dmortal.artgallery.databinding.DataItemBinding

class DataAdapter: androidx.recyclerview.widget.ListAdapter<DataDTO, DataViewHolder>(itemComparator) {

	var galleryData: MutableList<DataDTO> = mutableListOf()
	// image quality standards : 200, 400, 600, 843, 1686
	var quality = "200"
	var scaleType: PersistentSettingsEntity.SCALE_TYPE =
		PersistentSettingsEntity.SCALE_TYPE.fitXY

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = DataItemBinding.inflate(layoutInflater, parent, false)
		return DataViewHolder(binding)
	}

	override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
		holder.bind(getItem(position), scaleType, quality)
	}

	override fun getItemCount(): Int {
		return galleryData.size
	}

	private companion object {

		private val itemComparator = object : DiffUtil.ItemCallback<DataDTO>() {

			override fun areItemsTheSame(oldItem: DataDTO, newItem: DataDTO): Boolean {
				return oldItem.id == newItem.id
			}

			override fun areContentsTheSame(oldItem: DataDTO, newItem: DataDTO): Boolean {
				return oldItem.imageId == newItem.imageId
			}
		}
	}

}