package com.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.states.HomeUiState
import com.network.firebase.firestore.Firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val firestore: Firestore
) : ViewModel(), HomeInterface {

    private val _state = MutableStateFlow(HomeUiState())
    override val state: StateFlow<HomeUiState> = _state.asStateFlow()

    fun update(update: HomeUiState.() -> HomeUiState) {
        _state.value = update(_state.value)
    }

    override fun getPlayer(playerId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firestore.getPlayer(playerId).let { update { copy(player = it) } }
        }
    }
}