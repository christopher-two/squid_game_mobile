package com.christopher_two.start

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StartViewModel: ViewModel() {
    data class StartState(
        var isVisible: Boolean = false
    )
    private val _state = MutableStateFlow(StartState())
    val state = _state.asStateFlow()

    fun update(update: StartState.() -> StartState) {
        _state.value = update(_state.value)
    }
}