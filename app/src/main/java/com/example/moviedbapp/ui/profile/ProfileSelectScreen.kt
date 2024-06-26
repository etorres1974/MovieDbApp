package com.example.moviedbapp.ui.profile

import android.widget.Toast
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviedbapp.R
import com.example.moviedbapp.data.room.Profile
import com.example.moviedbapp.ui.application.AppViewModelProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

interface ProfileSelectNavigator {
    fun logout()
    fun home()
}

@Composable
fun ProfileSelectScreen(
    navigator: ProfileSelectNavigator,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.profileUiStateStateFlow.collectAsState()
    ProfileSelectScreenContent(profileUiState = homeUiState, modifier, navigator)
}

@Preview(showBackground = true)
@Composable
fun ProfileSelectScreenPreview(modifier: Modifier = Modifier) {
    val previewState = ProfileUiState(
        profiles = listOf(Profile("name", true), Profile("name", false)),
        actions = object : ProfileSelectScreenActions {
            override fun onProfileNameChanged(newValue: String) {}
            override fun addProfile(name: String) {}
            override fun selectProfile(profile: Profile) {}
            override fun deleteProfile(profile: Profile) {}
            override fun logout(onSuccess: () -> Unit) {}
        }
    )
    ProfileSelectScreenContent(profileUiState = previewState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileSelectScreenContent(
    profileUiState: ProfileUiState,
    modifier: Modifier = Modifier,
    navigator: ProfileSelectNavigator? = null,
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
    ) {

        Button(
            onClick = {
                profileUiState.logout {
                    navigator?.logout()
                }
            },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(text = "Logout")
        }
        val profileName by profileUiState.inputState
        if (profileUiState.canAddMoreProfiles) {
            OutlinedTextField(
                value = profileName.profileName,
                onValueChange = { profileUiState.onProfileNameChanged(it)},
                label = { Text("New Profile Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { profileUiState.addProfile("New Profile") },
                enabled = true,
                shape = MaterialTheme.shapes.small,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(text = "Add Profile")
            }
        } else {
            Text(text = "Max Profiles Reached")
        }
        var uiSelectedProfile by remember { mutableStateOf<Profile?>(null) }
        ProfilesList(
            uiSelectedProfile = uiSelectedProfile,
            itemList = profileUiState.profiles,
            onItemClick = { uiSelectedProfile = it },
            modifier = Modifier
        )
        Spacer(modifier = Modifier.weight(1f))
        uiSelectedProfile?.let { uiSelected ->
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .padding(12.dp)
            ) {
                Button(
                    interactionSource = LongPressButtonInteraction(
                        onShortPress = {
                            Toast.makeText(context, "Long click to delete", Toast.LENGTH_SHORT)
                                .show()
                        },
                        onLongPress = {
                            profileUiState.deleteProfile(uiSelected)
                            uiSelectedProfile = null
                        }),
                    onClick = {},
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(text = "Delete Profile")
                }

                Button(
                    modifier = Modifier.padding(4.dp),
                    onClick = {
                        profileUiState.selectProfile(uiSelected)
                        navigator?.home()
                    }) {
                    Text(text = "Enter Profile")
                }
            }
        }
    }
}

@Composable
fun LongPressButtonInteraction(
    onLongPress: () -> Unit = {},
    onShortPress: () -> Unit = {}
): MutableInteractionSource {
    val interactionSource = remember { MutableInteractionSource() }
    val viewConfiguration = LocalViewConfiguration.current

    LaunchedEffect(interactionSource) {
        var isLongClick = false
        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    isLongClick = false
                    delay(viewConfiguration.longPressTimeoutMillis)
                    isLongClick = true
                    onLongPress()
                }

                is PressInteraction.Release -> {
                    if (isLongClick.not()) {
                        onShortPress()
                    }
                }
            }
        }
    }
    return interactionSource
}

@Composable
private fun ProfilesList(
    uiSelectedProfile: Profile?,
    itemList: List<Profile>,
    onItemClick: (Profile) -> Unit,
    modifier: Modifier = Modifier
) {
    val highlightColor = highlightColor()
    LazyColumn(
        modifier = modifier,
    ) {

        items(items = itemList, key = { it.id }) { item ->
            val dynamicModifier = if (item.id == uiSelectedProfile?.id) modifier.drawWithContent {
                drawContent()
                drawRect(color = highlightColor)
            } else modifier
            ProfileItem(item = item,
                modifier = dynamicModifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item) })
        }
    }
}

@Composable
private fun highlightColor(): Color {
    val primaryColor = MaterialTheme.colorScheme.primary
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.2f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "blinking"
    )
    return primaryColor.copy(alpha = alpha)
}

@Composable
private fun ProfileItem(
    item: Profile, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = item.id.toString(),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = item.selected.toString(),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


