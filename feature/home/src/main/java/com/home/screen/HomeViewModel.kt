package com.home.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.states.HomeUiState
import com.network.firebase.firestore.Firestore
import com.network.firebase.models.Player
import com.network.firebase.realtime.RealtimeDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val firestore: Firestore,
    private val realtimeDatabase: RealtimeDatabase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    suspend fun loadData(
        args: String
    ) {
        update { HomeUiState(isLoading = true) }
        val player = getPlayer(args)
        val updates = mapOf("isActive" to true)
        realtimeDatabase.updatePlayerStatus(
            updates = updates,
            playerId = args
        )
        listenToStatusPlayer(args)
        update {
            copy(
                player = player,
                isLoading = false
            )
        }
    }

    fun update(update: HomeUiState.() -> HomeUiState) {
        _state.value = update(_state.value)
    }

    suspend fun getPlayer(playerId: String): Player? {
        return firestore.getPlayer(playerId)
    }

    private fun listenToStatusPlayer(playerId: String) {
        viewModelScope.launch {
            realtimeDatabase.observePlayerStatus(
                playerId = playerId
            ).collect { statusPlayer ->
                Log.d("HomeViewModel", "Received statusPlayer: $statusPlayer")
                update { copy(statusPlayer = statusPlayer) }
            }
        }
    }
}