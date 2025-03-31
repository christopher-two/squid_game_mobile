package com.christopher_two.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.christopher_two.login.components.OtpActions
import com.christopher_two.login.components.OtpInputField
import com.shared.utils.routes.RoutesStart
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(navController: NavController) = Screen(navController = navController)

@Composable
internal fun Screen(
    viewModel: LoginViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val focusRequesters = remember {
        List(4) { FocusRequester() }
    }
    val focusedManger = LocalFocusManager.current
    val keyboardManger = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.focusedIndex) {
        state.focusedIndex?.let {
            focusRequesters.getOrNull(it)?.requestFocus()
        }
    }

    LaunchedEffect(state.code, keyboardManger) {
        val allNumbersEntered = state.code.none { it == null }
        if (allNumbersEntered) {
            focusRequesters.forEach { it.freeFocus() }
            focusedManger.clearFocus()
            keyboardManger?.hide()
        }
    }

    if (!state.isLoading) {
        OtpScreen(
            state = state,
            focusRequesters = focusRequesters,
            modifier = Modifier.background(colorScheme.background),
            onAction = { action ->
                when (action) {
                    is OtpActions.OnEnterNumber -> {
                        if (action.number != null) {
                            focusRequesters[action.index].freeFocus()
                        }
                    }

                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
    } else {
        CircularProgressIndicator()
    }

    if (state.isValid != null) {
        navController.navigate(
            route = "${RoutesStart.Camera.route}/${state.isValid}",
        )
    } else {
        viewModel.update { copy(isLoading = false) }
    }
}

@Composable
internal fun OtpScreen(
    state: LoginViewModel.LoginState,
    focusRequesters: List<FocusRequester>,
    onAction: (OtpActions) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            state.code.forEachIndexed { index, number ->
                OtpInputField(
                    number = number,
                    focusRequester = focusRequesters[index],
                    onFocusChanged = { isFocused ->
                        if (isFocused) {
                            onAction(OtpActions.OnChangedFieldFocused(index))
                        }
                    },
                    onNumberChanged = {
                        onAction(OtpActions.OnEnterNumber(it, index))
                    },
                    onKeyboardBack = {
                        onAction(OtpActions.OnKeyboardBack)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            }
        }
    }
}
