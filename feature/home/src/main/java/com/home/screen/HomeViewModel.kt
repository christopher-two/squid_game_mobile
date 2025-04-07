package com.home.screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.states.HomeUiState
import com.network.firebase.models.StatusPlayer
import com.network.firebase.realtime.RealtimeDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel(
    private val realtimeDatabase: RealtimeDatabase,
    private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    fun loadData(
        args: String
    ) {
        update { HomeUiState(isLoading = true) }
        Log.d("HomeViewModel", "Loading data with args: $args")
        update {
            copy(
                statusPlayer = createPlayer(uuid = args),
                isLoading = false,
            )
        }
    }

    fun update(update: HomeUiState.() -> HomeUiState) {
        _state.value = update(_state.value)
    }

    fun createPlayer(uuid: String): StatusPlayer {
        val statusPlayer = StatusPlayer(
            isAlive = true,
            isWinner = false,
            isActive = true,
            image = "https://firebasestorage.googleapis.com/v0/b/horus-d67b2.firebasestorage.app/o/photos%2F$uuid.jpg?alt=media&token=13b05306-f2b0-4b21-acf8-98eb4d5a211c",
            numPlayer = Random.nextInt(1, 365).toString(),
            uuid = uuid
            )
        realtimeDatabase.createPlayer(
            player = statusPlayer
        )
        return statusPlayer
    }

    fun listenToStatusPlayer(playerId: String) {
        viewModelScope.launch {
            realtimeDatabase.observePlayerStatus(
                playerId = playerId
            ).collect { statusPlayer ->
                Log.d("HomeViewModel", "Received statusPlayer: $statusPlayer")
                update {
                    copy(
                        statusPlayer = this.statusPlayer?.copy(
                            isAlive = statusPlayer.isAlive,
                            isWinner = statusPlayer.isWinner,
                            isActive = statusPlayer.isActive
                        )
                    )
                }
            }
        }
    }
}