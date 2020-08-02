package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.repositories.PreferencesRepository
import ru.skillbranch.devintensive.utils.Profile

class ProfileViewModel: ViewModel() {

    val repository = PreferencesRepository
    val profileData = MutableLiveData<Profile>()
    val nightThemeData = MutableLiveData<Boolean>()

    init{
        profileData.value = repository.getProfileData()
        nightThemeData.value = repository.getNightThemeData()
    }

    fun getIsNightTheme(): LiveData<Boolean> = nightThemeData

    fun saveIsNightTheme(isNightTheme: Boolean){
        repository.saveIsNightTheme(isNightTheme)
        nightThemeData.value = isNightTheme
    }

    fun getProfileData(): LiveData<Profile> = profileData

    fun saveProfileData(profile: Profile){
        repository.saveProfileData(profile)
        profileData.value = profile
    }
}