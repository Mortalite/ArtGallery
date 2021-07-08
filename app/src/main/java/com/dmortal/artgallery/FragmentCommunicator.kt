package com.dmortal.artgallery

import com.dmortal.artgallery.db.DBManager
import com.dmortal.artgallery.ds.MainData
import com.dmortal.artgallery.ds.Settings

interface FragmentCommunicator {
    fun getMainData(): MainData?
    fun isFirstCreate(): Boolean
    fun setIsFirstCreate(value: Boolean)
    fun makeToast(message: String)
    fun openGalleryFragment()
}