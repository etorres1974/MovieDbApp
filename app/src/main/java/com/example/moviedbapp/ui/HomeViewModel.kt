package com.example.moviedbapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.data.room.Profile
import com.example.moviedbapp.data.ProfileRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class HomeViewModel(
    private val profileRepository : ProfileRepository
) : ViewModel() {

    val homeUiStateStateFlow : StateFlow<HomeUiState> = profileRepository.allProfiles
        .map { profiles ->
            HomeUiState(
                profiles = profiles
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )

    fun addProfile(){
        viewModelScope.launch {
            profileRepository.addProfile("Profile Name")
        }
    }

    fun selectProfile(profile: Profile){
        viewModelScope.launch {
            profileRepository.selectProfile(profile)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(
    val profiles: List<Profile> = listOf()
)