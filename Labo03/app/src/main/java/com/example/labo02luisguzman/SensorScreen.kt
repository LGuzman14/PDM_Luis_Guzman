import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.BoxWithConstraints
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/**
 * Componente de Barra de Progreso que calcula su ancho de manera escalable
 * utilizando las dimensiones reales del contenedor padre.
 *
 * @param progressRatio El progreso actual como un valor flotante entre 0.0f y 1.0f.
 * @param backgroundColor El color de fondo de la barra.
 * @param foregroundColor El color del progreso activo.
 * @param modifier El modificador para aplicar al contenedor principal.
 */
@Composable
fun ScalableProgressBar(
    progressRatio: Float,
    backgroundColor: Color = Color.LightGray.copy(alpha = 0.5f),
    foregroundColor: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    // Aseguramos que el ratio esté siempre entre 0 y 1.
    val safeRatio = progressRatio.coerceIn(0f, 1f)

    // 1. Usamos BoxWithConstraints para obtener las dimensiones reales del contenedor padre.
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {

        // 2. Obtenemos la densidad local para realizar la conversión de píxeles (Px) a unidades Dp.
        val density = LocalDensity.current

        // 3. Calculamos el ancho total disponible en Dp.
        // maxWidth es el ancho máximo disponible en píxeles (Px).
        val totalWidthDp = with(density) { maxWidth.toDp() }

        // 4. Calculamos el ancho del progreso:
        // Multiplicamos el ancho total por el ratio.
        // Esto es crucial porque garantiza que el progreso nunca exceda el ancho disponible.
        val progressWidth = totalWidthDp * safeRatio

        // Usamos Box para superponer dos rectángulos: el fondo y el progreso.
        Box(modifier = Modifier
            .height(MaterialTheme.typography.bodyMedium.fontSize.dp) // Define una altura fija para la barra
            .background(backgroundColor)
        ) {
            // El rectángulo de progreso va superpuesto en el centro.
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(progressWidth)
                    .background(foregroundColor)
            )
        }
    }
}

// --- Ejemplo de Uso en un Composable Principal ---
@Composable
fun ProgressBarDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Progreso 1 (50%):", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        // Uso de la barra de progreso con 50% de avance
        ScalableProgressBar(
            progressRatio = 0.5f,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("Progreso 2 (10%):", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        // Uso de la barra de progreso con 10% de avance
        ScalableProgressBar(
            progressRatio = 0.1f,
            foregroundColor = Color.Red
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("Progreso 3 (100%):", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        // Uso de la barra de progreso con 100% de avance
        ScalableProgressBar(
            progressRatio = 1.0f,
            foregroundColor = Color.Green
        )
    }
}