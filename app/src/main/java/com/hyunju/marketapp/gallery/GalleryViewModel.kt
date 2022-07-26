package com.hyunju.marketapp.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyunju.marketapp.MarketApplication.Companion.appContext
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {

    private val galleryPhotoRepository by lazy { GalleryPhotoRepository(appContext!!) }

    private lateinit var photoList: MutableList<GalleryPhoto>

    val galleryStateLiveData = MutableLiveData<GalleryState>(GalleryState.Uninitialized)

    fun fetchData() = viewModelScope.launch {
        setState(
            GalleryState.Loading
        )
        photoList = galleryPhotoRepository.getAllPhotos()
        setState(
            GalleryState.Success(
                photoList
            )
        )
    }

    fun selectPhoto(galleryPhoto: GalleryPhoto) {
        val findGalleryPhoto = photoList.find { it.id == galleryPhoto.id }
        findGalleryPhoto?.let { photo ->
            photoList[photoList.indexOf(photo)] =
                photo.copy(
                    isSelected = photo.isSelected.not()
                )
            setState(
                GalleryState.Success(
                    photoList
                )
            )
        }
    }

    fun confirmCheckedPhotos() {
        setState(
            GalleryState.Loading
        )
        setState(
            GalleryState.Confirm(
                photoList = photoList.filter { it.isSelected }
            )
        )
    }

    private fun setState(state: GalleryState) {
        galleryStateLiveData.postValue(state)
    }

}