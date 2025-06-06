import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorSwitcherApp()
        }
    }
}

@Composable
fun ColorSwitcherApp() {
    val googleColors = listOf(
        Color(0xFF4285F4), // Blue
        Color(0xFFEA4335), // Red
        Color(0xFFFBBC05), // Yellow
        Color(0xFF34A853)  // Green
    )

    var colorIndex by remember { mutableStateOf(0) }
    val currentColor = googleColors[colorIndex % googleColors.size]

    val activity = LocalContext.current as Activity
    val coroutineScope = rememberCoroutineScope()

    // Exit button animation state
    var animateExit by remember { mutableStateOf(false) }
    val offsetX by animateDpAsState(
        targetValue = if (animateExit) 100.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Center button
        Button(
            onClick = { colorIndex++ },
            colors = ButtonDefaults.buttonColors(containerColor = currentColor),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text("Color", color = Color.White)
        }

        // Bottom Exit button with animation
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(x = offsetX)
        ) {
            Button(
                onClick = {
                    animateExit = true
                    coroutineScope.launch {
                        delay(1000) // wait 1 second
                        activity.finish() // exit the app
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text("Exit")
            }
        }
    }
}
