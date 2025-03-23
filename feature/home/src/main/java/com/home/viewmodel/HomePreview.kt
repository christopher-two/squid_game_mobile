package com.home.viewmodel

import androidx.lifecycle.ViewModel
import com.home.states.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomePreview() : HomeInterface, ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    override val state: StateFlow<HomeUiState>
        get() = _state.asStateFlow()

    override fun getPlayer(playerId: String) {
        TODO("Not yet implemented")
    }
}