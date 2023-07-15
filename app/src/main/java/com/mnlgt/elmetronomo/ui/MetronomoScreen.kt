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
import kotlinx.coroutines.launch


@Composable
fun MetronomoScreen(modifier: Modifier, viewModel: MetronomoViewModel,  windowSize: WindowWidthSizeClass) {

    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()



    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { coroutineScope.launch { viewModel.aprietaBoton() } },
                shape = CircleShape,
                modifier = Modifier.size(140.dp)
            ) {

                Icon(
                    imageVector = if (uiState.andando) Icons.Rounded.Close else Icons.Rounded.PlayArrow,
                    contentDescription =
                    if (uiState.andando) stringResource(id = R.string.detener)
                    else stringResource(id = R.string.iniciar),
                    modifier = Modifier.size(180.dp),
                    tint = MaterialTheme.colorScheme.background
                )
            }
            Text(
                text = if (uiState.andando) stringResource(id = R.string.detener)
                else stringResource(id = R.string.iniciar),
                modifier = Modifier.padding(10.dp),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic
            )

        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "BPM:",
                    modifier = Modifier
                        .padding(end = 10.dp, bottom = 3.dp),
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = uiState.bpm.toInt().toString(),
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.width(60.dp),
                    textAlign = TextAlign.Right
                )
            }


            Slider(
                modifier = Modifier.padding(start = 30.dp, end = 30.dp, bottom = 30.dp),
                value = uiState.bpm,
                onValueChange = { tempo -> viewModel.setTempo(tempo) },
                valueRange = 10F..260F,
                colors = SliderDefaults.colors()
            )

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "ACENTO:",
                    modifier = Modifier
                        .padding(end = 10.dp, bottom = 3.dp),
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = uiState.acento.toString(),
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.width(40.dp),
                    textAlign = TextAlign.Right
                )
            }

            Slider(
                modifier = Modifier.padding(start = 30.dp, end = 30.dp, bottom = 30.dp),
                value = uiState.acento.toFloat(),
                onValueChange = { a -> viewModel.setAcento(a.toInt()) },
                valueRange = 1F..14F,
                colors = SliderDefaults.colors(),
                steps = 12
            )

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "SUBDIVISION:",
                    modifier = Modifier
                        .padding(end = 10.dp, bottom = 3.dp),
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = uiState.subdivision.toString(),
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.width(40.dp),
                    textAlign = TextAlign.Right
                )
            }

            Slider(
                modifier = Modifier.padding(start = 30.dp, end = 30.dp, bottom = 30.dp),
                value = uiState.subdivision.toFloat(),
                onValueChange = {a -> viewModel.setSubdivision(a.toInt())  },
                valueRange = 1F..4F,
                colors = SliderDefaults.colors(),
                steps = 2
            )
        }

    }
}
