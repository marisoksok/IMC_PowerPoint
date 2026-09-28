package com.example.imc_powerpoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.imc_powerpoint.ui.theme.IMC_PowerPointTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IMC_PowerPointTheme {
                    AppIMC()
            }
        }
    }
}

@Composable
fun PantallaCalculadora(alCalcular: (Double) -> Unit) {
    var peso by remember { mutableStateOf("70") }
    var altura by remember { mutableStateOf("170") }
    var imc by remember { mutableStateOf(0.0) }

    Column {
        Text("SALUD & BIENESTAR")
        Text("Calculadora de IMC")
        Text("Índice de Masa Corporal")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Peso - kg")
        TextField(value = peso, onValueChange = { peso = it })
        Text(text = "Altura - cm")
        TextField(value = altura, onValueChange = { altura = it })

        Button(
            onClick = {
                val alturaMetros = (altura.toDoubleOrNull() ?: 0.0) * 0.01
                val pesoD = peso.toDoubleOrNull() ?: 0.0
                imc = pesoD / (alturaMetros * alturaMetros)
                alCalcular(imc)
            }
        ) {
            Text("Calcular IMC")
        }
            Text(text = "IMC: %.2f".format(imc))
    }
}

@Composable
fun AppIMC() {
    // 1. Estado para saber en qué pantalla estamos (0 = Calculadora, 1 = Resultado)
    var numeroDePantalla by remember { mutableStateOf(0) }

    // 2. Estado para guardar el valor del IMC calculado y poder pasarlo a la otra pantalla
    var resultado by remember { mutableStateOf(0.0) }

    // 3. Decidimos qué mostrar según el valor de numeroDePantalla
    when (numeroDePantalla) {
        0 -> PantallaCalculadora(
            alCalcular = { imc ->       // <--- 'imc' es el valor que nos envía la calculadora
                resultado = imc         // <--- AQUÍ VA EL ESPACIO EN BLANCO
                numeroDePantalla = 1    // Cambiamos al estado 1 para mostrar el resultado
            }
        )
        1 -> PantallaResultado(resultado) // Mostramos la pantalla de resultado con el valor guardado
    }
}


@Composable
fun PantallaResultado(imc: Double){
    Column() {
        Text("Tu IMC es: ")
        Text(imc.toString())
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaCalculadoraPreview() {
    IMC_PowerPointTheme {
        AppIMC()
    }
}