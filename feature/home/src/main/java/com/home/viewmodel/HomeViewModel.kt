package com.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.states.HomeUiState
import com.network.firebase.firestore.Firestore
import com.network.firebase.models.Player
import com.network.firebase.models.StatusPlayer
import com.network.firebase.realtime.RealtimeDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val firestore: Firestore,
    private val realtimeDatabase: RealtimeDatabase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()
        .onStart { loadData() }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = HomeUiState()
        )

    private fun loadData() {
        update { copy(isLoading = true) }
        viewModelScope.launch {
            val player = getPlayer("Yk3Rn5LN0w7GM6vVMVr3")
            val updates = mapOf("isActive" to true)
            realtimeDatabase.updatePlayerStatus(
                updates = updates,
                playerId = "Yk3Rn5LN0w7GM6vVMVr3"
            )
            update { copy(player = player) }
            listenToStatusPlayer("Yk3Rn5LN0w7GM6vVMVr3")
        }
        update { copy(isLoading = false) }
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