package com.christopher_two.login

import androidx.lifecycle.ViewModel
import com.christopher_two.login.components.OtpActions
import com.shared.utils.enums.KeysTensorflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.enums.EnumEntries

class LoginViewModel : ViewModel() {
    data class LoginState(
        val code: List<Int?> = (1..4).map { null },
        val focusedIndex: Int? = 0,
        val isValid: KeysTensorflow? = null,
        val codeValid: EnumEntries<KeysTensorflow> = KeysTensorflow.entries,
        val isLoading: Boolean = false
    )

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun update(update: LoginState.() -> LoginState) {
        _state.value = _state.value.update()
    }

    fun onAction(action: OtpActions) {
        when (action) {
            is OtpActions.OnEnterNumber -> {
                enterNumber(action.number, action.index)
            }

            is OtpActions.OnChangedFieldFocused -> {
                update { copy(focusedIndex = action.index) }
            }

            OtpActions.OnKeyboardBack -> {
                val previousFocusedIndex = getPreviousFocusedIndex(_state.value.focusedIndex)
                update {
                    copy(
                        focusedIndex = previousFocusedIndex,
                        code = this.code.mapIndexed { index, number -> if (index == previousFocusedIndex) null else number }
                    )
                }
            }
        }
    }

    private fun enterNumber(number: Int?, index: Int) {
        val newCode = _state.value.code.mapIndexed { currentIndex, currentNumber ->
            if (currentIndex == index) number else currentNumber
        }
        val wasNumberRemoved = number == null
        val allFieldsFilled = newCode.none { it == null }
        val isValidCode = if (allFieldsFilled) {
            _state.value.codeValid.firstOrNull { it.key == newCode.joinToString("") }
        } else null
        update {
            copy(
                code = newCode,
                focusedIndex = if (wasNumberRemoved || this.code.getOrNull(index) != null) {
                    this.focusedIndex
                } else {
                    getNextFocusedTextFieldIndex(
                        currentFocusedIndex = this.focusedIndex,
                        currentCode = this.code
                    ) ?: index
                },
                isValid = isValidCode,
                isLoading = false // Adjust based on actual async requirements
            )
        }
    }

    private fun getPreviousFocusedIndex(currentIndex: Int?): Int? {
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getNextFocusedTextFieldIndex(
        currentFocusedIndex: Int?,
        currentCode: List<Int?>
    ): Int? {
        if (currentFocusedIndex == null) return null
        if (currentFocusedIndex == 3) return currentFocusedIndex
        return this.getFirstEmptyFieldIndexAfterFocusedIndex(
            currentFocusedIndex = currentFocusedIndex,
            code = currentCode
        )
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(
        currentFocusedIndex: Int,
        code: List<Int?>
    ): Int {
        code.forEachIndexed { index, number ->
            if (index <= currentFocusedIndex) return@forEachIndexed
            if (number == null) return index
        }
        return currentFocusedIndex
    }
}