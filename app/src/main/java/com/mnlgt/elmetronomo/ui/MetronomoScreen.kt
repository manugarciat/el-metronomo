package com.mnlgt.elmetronomo.ui

import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mnlgt.elmetronomo.R
import com.mnlgt.elmetronomo.ui.theme.ElMetronomoTheme
import com.mnlgt.elmetronomo.ui.theme.PurpleGrey40
import kotlinx.coroutines.launch


@Composable
fun MetronomoScreen(modifier: Modifier, viewModel: MetronomoViewModel) {

    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { coroutineScope.launch { viewModel.aprietaBoton() } },
            shape = CircleShape,
            modifier = Modifier.size(150.dp)
        ) {

            Icon(
                imageVector = if (uiState.andando) Icons.Default.Close else Icons.Default.PlayArrow,
                contentDescription = "content description",
                modifier = Modifier.size(80.dp)
            )


//            Text(
//                text = stringResource(id = if (uiState.andando) R.string.detener else R.string.iniciar),
//                color = MaterialTheme.colorScheme.secondary
//            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = uiState.bpm.toString(),
                fontSize = 40.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Slider(
                modifier = Modifier.padding(30.dp),
                value = uiState.bpm.toFloat(),
                onValueChange = { tempo -> viewModel.setTempo(tempo) },
                valueRange = 10F..260F,
                colors = SliderDefaults.colors()
            )
        }

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
            Slider(
                value = 60.0F,
                onValueChange = { },
                valueRange = 10F..500F
            )
            Text(text = "60")
        }
    }
}