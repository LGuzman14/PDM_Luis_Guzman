package com.example.labo02luisguzman

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun useSensor(sensorType: Int): List<Float> {
    val context = LocalContext.current

    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val sensor = remember(sensorType) {
        sensorManager.getDefaultSensor(sensorType)
    }

    var sensorValues by remember {
        mutableStateOf(listOf(0f, 0f, 0f))
    }

    DisposableEffect(sensorType) {
        if (sensor != null) {
            val listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    event?.values?.let { values ->
                        sensorValues = values.toList()
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            sensorManager.registerListener(
                listener,
                sensor,
                SensorManager.SENSOR_DELAY_UI
            )

            onDispose {
                sensorManager.unregisterListener(listener)
            }
        } else {
            onDispose { }
        }
    }

    return sensorValues
}

@Composable
fun SensorScreen(
    modifier: Modifier = Modifier,
    onVolver: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE0F2FE),
                        Color(0xFFF8FAFC)
                    )
                )
            )
            .padding(20.dp)
    ) {
        Text(
            text = "Sensores",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Lectura en tiempo real del sensor de luz y del giroscopio.",
            fontSize = 15.sp,
            color = Color(0xFF475569)
        )

        Spacer(modifier = Modifier.height(24.dp))

        LightSensor()

        Spacer(modifier = Modifier.height(20.dp))

        GyroscopeSensor()

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al menú")
        }
    }
}

@Composable
fun LightSensor() {
    val lightValues = useSensor(Sensor.TYPE_LIGHT)

    val light = lightValues.getOrNull(0) ?: 0f
    val progress = (light / 1000f).coerceIn(0f, 1f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Text(
                text = "Sensor de Luz",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Mide la intensidad de luz ambiental.",
                fontSize = 14.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "${String.format("%.2f", light)} lx",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF59E0B)
            )

            Spacer(modifier = Modifier.height(14.dp))

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
        }
    }
}

@Composable
fun GyroscopeSensor() {
    val gyroscopeValues = useSensor(Sensor.TYPE_GYROSCOPE)

    val x = gyroscopeValues.getOrNull(0) ?: 0f
    val y = gyroscopeValues.getOrNull(1) ?: 0f
    val z = gyroscopeValues.getOrNull(2) ?: 0f

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Text(
                text = "Giroscopio",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Detecta la rotación del dispositivo en los ejes X, Y y Z.",
                fontSize = 14.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(20.dp))

            SensorValueRow(
                eje = "X",
                valor = x
            )

            SensorValueRow(
                eje = "Y",
                valor = y
            )

            SensorValueRow(
                eje = "Z",
                valor = z
            )
        }
    }
}

@Composable
fun SensorValueRow(
    eje: String,
    valor: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Eje $eje",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF334155)
        )

        Text(
            text = String.format("%.4f", valor),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2563EB)
        )
    }
}