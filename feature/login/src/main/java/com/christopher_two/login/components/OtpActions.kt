package com.christopher_two.login.components

sealed interface OtpActions {
    data class OnEnterNumber(val number: Int?, val index: Int) : OtpActions
    data class OnChangedFieldFocused(val index: Int) : OtpActions
    data object OnKeyboardBack : OtpActions
}