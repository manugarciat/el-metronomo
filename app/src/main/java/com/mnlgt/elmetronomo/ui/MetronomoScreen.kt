package com.mnlgt.elmetronomo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mnlgt.elmetronomo.R
import com.mnlgt.elmetronomo.data.rangoTempo
import kotlinx.coroutines.launch


@Composable
fun MetronomoScreen(
    viewModel: MetronomoViewModel,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                BotonMetronomo(
                    { coroutineScope.launch { viewModel.aprietaBoton() } },
                    uiState.andando
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MetronomoSlider(
                        stringResource(id = R.string.bpm).uppercase(),
                        uiState.bpm,
                        rangoTempo,
                        0,
                        funcOnValueChange = { t -> viewModel.setTempo(t) }
                    )

                    MetronomoSlider(
                        stringResource(id = R.string.acento).uppercase(),
                        uiState.acento.toFloat(),
                        1F..14F,
                        12,
                        funcOnValueChange = { a -> viewModel.setAcento(a.toInt()) }
                    )

                    MetronomoSlider(
                        stringResource(id = R.string.subdivision).uppercase(),
                        uiState.subdivision.toFloat(),
                        1F..4F,
                        2,
                        funcOnValueChange = { s -> viewModel.setSubdivision(s.toInt()) }
                    )
                }
            }
        }

        else -> {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                BotonMetronomo(
                    { coroutineScope.launch { viewModel.aprietaBoton() } },
                    uiState.andando,
                    modifier = Modifier.padding(start = 80.dp, end = 60.dp)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 80.dp, top = 5.dp)
                ) {
                    MetronomoSlider(
                        stringResource(id = R.string.bpm).uppercase(),
                        uiState.bpm,
                        rangoTempo,
                        0,
                        funcOnValueChange = { t -> viewModel.setTempo(t) }
                    )

                    MetronomoSlider(
                        stringResource(id = R.string.acento).uppercase(),
                        uiState.acento.toFloat(),
                        1F..14F,
                        12,
                        funcOnValueChange = { a -> viewModel.setAcento(a.toInt()) }
                    )

                    MetronomoSlider(
                        stringResource(id = R.string.subdivision).uppercase(),
                        uiState.subdivision.toFloat(),
                        1F..4F,
                        2,
                        funcOnValueChange = { s -> viewModel.setSubdivision(s.toInt()) }
                    )
                }
            }
        }

    }
}

@Composable
private fun BotonMetronomo(
    funcOnClick: () -> Unit,
    estaPrendido: Boolean,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Button(
            onClick = funcOnClick,
            shape = CircleShape,
            modifier = Modifier.size(140.dp)
        ) {

            Icon(
                imageVector = if (estaPrendido) Icons.Rounded.Close else Icons.Rounded.PlayArrow,
                contentDescription =
                if (estaPrendido) stringResource(id = R.string.detener)
                else stringResource(id = R.string.iniciar),
                modifier = Modifier.size(180.dp),
                tint = MaterialTheme.colorScheme.background
            )
        }
        Text(
            text = if (estaPrendido) stringResource(id = R.string.detener)
            else stringResource(id = R.string.iniciar),
            modifier = Modifier.padding(10.dp),
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun MetronomoSlider(
    texto: String,
    variable: Float,
    rango: ClosedFloatingPointRange<Float>,
    pasos: Int,
    funcOnValueChange: (Float) -> Unit

) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = texto,
            modifier = Modifier
                .padding(end = 10.dp, bottom = 3.dp),
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = variable.toInt().toString(),
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Light,
            modifier = Modifier.width(60.dp),
            textAlign = TextAlign.Right
        )
    }
    Slider(
        modifier = Modifier.padding(start = 30.dp, end = 30.dp, bottom = 30.dp),
        value = variable,
        onValueChange = funcOnValueChange,
        valueRange = rango,
        colors = SliderDefaults.colors(
            //thumbColor = MaterialTheme.colorScheme.secondary,
            //activeTrackColor = MaterialTheme.colorScheme.secondary,
            inactiveTrackColor = MaterialTheme.colorScheme.tertiary
        ),
        steps = pasos

    )
}
