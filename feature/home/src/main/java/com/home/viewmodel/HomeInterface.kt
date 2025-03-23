package com.home.viewmodel

import com.home.states.HomeUiState
import kotlinx.coroutines.flow.StateFlow

interface HomeInterface {
    val state: StateFlow<HomeUiState>
    fun getPlayer(playerId: String)
}