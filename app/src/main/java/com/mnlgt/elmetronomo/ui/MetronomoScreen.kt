package com.mnlgt.elmetronomo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mnlgt.elmetronomo.Metronomo
import com.mnlgt.elmetronomo.R
import com.mnlgt.elmetronomo.tictoc
import com.mnlgt.elmetronomo.ui.theme.ElMetronomoTheme
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Composable
fun MetronomoScreen(modifier: Modifier, metronomo: Metronomo, viewModel: MetronomoViewModel) {
    val coroutineScope = rememberCoroutineScope()

    Column() {

        Button(onClick = { suspend { viewModel.iniciar() } }) {
            Text(text = stringResource(R.string.iniciar))
        }
//        Button(onClick = { coroutineScope.cancel() }) {
//            Text(text = "Stop")
//        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ElMetronomoTheme {
        //App(soundP, 3, 1)
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