package com.mnlgt.elmetronomo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mnlgt.elmetronomo.R
import com.mnlgt.elmetronomo.ui.theme.ElMetronomoTheme
import kotlinx.coroutines.launch


@Composable
fun MetronomoScreen(modifier: Modifier, viewModel: MetronomoViewModel) {

    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    Column() {

        Button(onClick = { coroutineScope.launch { viewModel.aprietaBoton() } }) {
            Text(text = stringResource(id = if (uiState.andando) R.string.detener else R.string.iniciar))
        }
        Slider(
            modifier = modifier,
            value = uiState.bpm.toFloat(),
            onValueChange = { tempo -> viewModel.setTempo(tempo) },
            valueRange = 10F..500F
        )
        Text(text = uiState.bpm.toString())

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ElMetronomoTheme {
        Column() {

            Button(onClick = { }) {
                Text(text = "Start")
            }
            Button(onClick = { }) {
                Text(text = "Stop")
            }
        }
    }
}