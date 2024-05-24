package com.example.moviedbapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.data.ProfileRepository
import com.example.moviedbapp.data.room.Profile
import com.example.moviedbapp.data.room.ProfileAndWatchList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val combineFlow = combine(
        profileRepository.allProfiles,
        profileRepository.selectedProfile
    ) { allProfiles, selectedProfile ->
        HomeUiState(
            selectedProfile = selectedProfile.firstOrNull(),
            profiles = allProfiles,
            canAddMoreProfiles = allProfiles.size < 4,
            addProfile = ::addProfile,
            selectProfile = ::selectProfile,
            deleteProfile =  ::deleteProfile
        )
    }

    val homeUiStateStateFlow: StateFlow<HomeUiState> = combineFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )

    private fun addProfile(profileName : String) {
        viewModelScope.launch {
            profileRepository.addProfile("Profile Name")
        }
    }

    private fun selectProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.selectProfile(profile)
        }
    }

    private fun deleteProfile(profile: Profile){
        viewModelScope.launch {
            profileRepository.deleteProfile(profile)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(
    val selectedProfile: ProfileAndWatchList? = null,
    val profiles: List<Profile> = listOf(),
    val canAddMoreProfiles : Boolean = true,
    val addProfile: (String) -> Unit = {},
    val selectProfile: (Profile) -> Unit = {},
    val deleteProfile : (Profile) -> Unit = {}
)