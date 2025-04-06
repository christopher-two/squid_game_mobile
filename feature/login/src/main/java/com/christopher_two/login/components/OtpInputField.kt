package com.christopher_two.login.components

import android.view.KeyEvent.KEYCODE_DEL
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly

@Composable
internal fun OtpInputField(
    number: Int? = null,
    focusRequester: FocusRequester,
    onFocusChanged: (Boolean) -> Unit,
    onNumberChanged: (Int?) -> Unit,
    onKeyboardBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val text by remember(number) {
        mutableStateOf(
            TextFieldValue(
                text = number?.toString().orEmpty(),
                selection = TextRange(
                    if (number != null) 1 else 0
                )
            )
        )
    }

    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = colorScheme.onBackground,
            )
            .background(Color.Transparent),
        contentAlignment = Alignment.Center,
        content = {
            BasicTextField(
                value = text,
                onValueChange = { newText ->
                    val newNumber = newText.text
                    if (newNumber.length <= 1 && newNumber.isDigitsOnly()) {
                        onNumberChanged(newNumber.toIntOrNull())
                    }
                },
                cursorBrush = SolidColor(colorScheme.onBackground),
                singleLine = true,
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp,
                    color = colorScheme.onBackground,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                decorationBox = { innerTextField ->
                    innerTextField()
                    if (!isFocused && number == null) {
                        Text(
                            text = "-",
                            textAlign = TextAlign.Center,
                            color = colorScheme.onBackground,
                            fontWeight = FontWeight.Light,
                            fontSize = 36.sp,
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize()
                        )
                    } else {
                        innerTextField()
                    }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isFocused = it.isFocused
                        onFocusChanged(it.isFocused)
                    }
                    .onKeyEvent { event ->
                        val oldPressDelete = event.nativeKeyEvent.keyCode == KEYCODE_DEL
                        if (oldPressDelete && number == null)
                            onKeyboardBack()
                        false
                    },

                )
        }
    )
}