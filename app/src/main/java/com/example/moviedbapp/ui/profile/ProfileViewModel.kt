package com.example.moviedbapp.ui.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.domain.ProfileRepository
import com.example.moviedbapp.data.room.Profile
import com.example.moviedbapp.data.room.ProfileAndWatchList
import com.example.moviedbapp.domain.UserRepository
import com.example.moviedbapp.ui.application.launchCatching
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val userRepository: UserRepository
) : ViewModel(), ProfileSelectScreenActions {

    private var inputState = mutableStateOf(ProfileSelectInputState())

    override fun onProfileNameChanged(newValue : String){
        inputState.value = inputState.value.copy(
            profileName = newValue
        )
    }

    private val combineFlow = combine(
        profileRepository.allProfiles,
        profileRepository.selectedProfile
    ) { allProfiles, selectedProfile ->
        ProfileUiState(
            inputState = inputState,
            selectedProfile = selectedProfile.firstOrNull(),
            profiles = allProfiles,
            canAddMoreProfiles = allProfiles.size < 4,
            actions = this
        )
    }

    val profileUiStateStateFlow: StateFlow<ProfileUiState> = combineFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ProfileUiState(actions = this)
        )

    override fun addProfile(profileName : String) {
        launchCatching {
            profileRepository.addProfile(inputState.value.profileName)
        }
    }

    override fun selectProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.selectProfile(profile)
        }
    }

    override fun deleteProfile(profile: Profile){
        viewModelScope.launch {
            profileRepository.deleteProfile(profile)
        }
    }

    override fun logout(onSuccess : () -> Unit){
        viewModelScope.launch {
            userRepository.logout()
            onSuccess()
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
data class ProfileSelectInputState(
    val profileName : String = ""
)
data class ProfileUiState(
    val inputState: MutableState<ProfileSelectInputState> = mutableStateOf(ProfileSelectInputState()),
    val selectedProfile: ProfileAndWatchList? = null,
    val profiles: List<Profile> = listOf(),
    val canAddMoreProfiles : Boolean = true,
    val actions : ProfileSelectScreenActions
) : ProfileSelectScreenActions by actions
interface ProfileSelectScreenActions{
    fun onProfileNameChanged(newValue : String)
    fun addProfile(name : String)
    fun selectProfile(profile : Profile)
    fun deleteProfile(profile: Profile)
    fun logout(onSuccess : () -> Unit)
}


