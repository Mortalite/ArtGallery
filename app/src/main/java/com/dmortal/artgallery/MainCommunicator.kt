package com.dmortal.artgallery

interface MainCommunicator {
    fun disableToolbarTitle()
    fun makeToast(message: String)
    fun openSettingsActivity()
    fun openGalleryFragment()
}