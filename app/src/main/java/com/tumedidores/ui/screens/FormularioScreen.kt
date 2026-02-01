package com.tumedidores.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tumedidores.R
import com.tumedidores.data.entity.TipoMedicion
import com.tumedidores.ui.viewmodels.MedicionViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    navController: NavController,
    viewModel: MedicionViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    LaunchedEffect(Unit) {
        viewModel.limpiarError()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.titulo_formulario)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        // ✅ AGREGADO: Scroll vertical para ver todo el formulario
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // TÍTULO
                Text(
                    text = "Selecciona el tipo de medidor:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // ✅ BOTONES MÁS PEQUEÑOS PARA CABER EN PANTALLA
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Botón AGUA
                    TipoButtonSimple(
                        tipo = TipoMedicion.AGUA,
                        isSelected = uiState.tipoSeleccionado == TipoMedicion.AGUA,
                        onClick = { viewModel.actualizarTipo(TipoMedicion.AGUA) }
                    )

                    // Botón LUZ
                    TipoButtonSimple(
                        tipo = TipoMedicion.LUZ,
                        isSelected = uiState.tipoSeleccionado == TipoMedicion.LUZ,
                        onClick = { viewModel.actualizarTipo(TipoMedicion.LUZ) }
                    )

                    // Botón GAS
                    TipoButtonSimple(
                        tipo = TipoMedicion.GAS,
                        isSelected = uiState.tipoSeleccionado == TipoMedicion.GAS,
                        onClick = { viewModel.actualizarTipo(TipoMedicion.GAS) }
                    )
                }

                // CAMPO DE VALOR
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Ingresa el valor de la lectura:",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = uiState.valor,
                            onValueChange = { viewModel.actualizarValor(it) },
                            label = { Text("Ejemplo: 150.75") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            placeholder = { Text("Escribe el número aquí") }
                        )
                    }
                }

                // OBSERVACIÓN (OPCIONAL)
                OutlinedTextField(
                    value = uiState.observacion,
                    onValueChange = { viewModel.actualizarObservacion(it) },
                    label = { Text("Observación (opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    minLines = 2
                )

                // INFORMACIÓN DE FECHA
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Fecha de lectura:",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = dateFormat.format(uiState.fechaSeleccionada),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Text(
                            text = "📅",
                            fontSize = 24.sp
                        )
                    }
                }

                // BOTONES DE ACCIÓN
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancelar")
                    }

                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                    Button(
                        onClick = {
                            viewModel.agregarMedicion()
                            navController.popBackStack()
                        },
                        modifier = Modifier.weight(1f),
                        enabled = uiState.valor.isNotBlank()
                    ) {
                        Text("Guardar")
                    }
                }

                // INDICADOR DE TIPO SELECCIONADO
                if (uiState.tipoSeleccionado != null) {
                    Text(
                        text = "Tipo seleccionado: ${when(uiState.tipoSeleccionado) {
                            TipoMedicion.AGUA -> "Agua 💧"
                            TipoMedicion.LUZ -> "Luz ⚡"
                            TipoMedicion.GAS -> "Gas 🔥"
                        }}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                // ✅ ESPACIO EXTRA PARA SCROLL
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// ✅ BOTÓN MÁS PEQUEÑO (80dp en lugar de 100dp)
@Composable
fun TipoButtonSimple(
    tipo: TipoMedicion,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = when (tipo) {
        TipoMedicion.AGUA -> Color(0xFF2196F3)
        TipoMedicion.LUZ -> Color(0xFFFFC107)
        TipoMedicion.GAS -> Color(0xFFF44336)
    }

    val emoji = when (tipo) {
        TipoMedicion.AGUA -> "💧"
        TipoMedicion.LUZ -> "⚡"
        TipoMedicion.GAS -> "🔥"
    }

    val nombre = when (tipo) {
        TipoMedicion.AGUA -> "Agua"
        TipoMedicion.LUZ -> "Luz"
        TipoMedicion.GAS -> "Gas"
    }

    Button(
        onClick = onClick,
        modifier = Modifier.size(80.dp), // ✅ MÁS PEQUEÑO (80dp)
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else Color.LightGray
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = emoji,
                fontSize = 24.sp // ✅ TEXTO MÁS PEQUEÑO
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = nombre,
                fontSize = 12.sp, // ✅ TEXTO MÁS PEQUEÑO
                textAlign = TextAlign.Center
            )
        }
    }
}