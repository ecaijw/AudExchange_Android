package com.luke.audexchange

import android.app.Activity
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ColorSwitcherApp(navController: NavHostController) {
    val googleColors = listOf(
        Color(0xFF4285F4), Color(0xFFEA4335),
        Color(0xFFFBBC05), Color(0xFF34A853), Color(0xFF673AB7)
    )

    var colorIndex by remember { mutableStateOf(0) }
    val currentColor = googleColors[colorIndex % googleColors.size]

    val activity = LocalContext.current as Activity
    val coroutineScope = rememberCoroutineScope()
    var animateExit by remember { mutableStateOf(false) }

    val offsetX by animateDpAsState(
        targetValue = if (animateExit) 100.dp else 0.dp,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Exchange Rate Button
        Button(
            onClick = { navController.navigate("exchange") },
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-90).dp)
        ) {
            Text("AUD Exchange Rate")
        }

        // Color Button
        Button(
            onClick = { colorIndex++ },
            colors = ButtonDefaults.buttonColors(containerColor = currentColor),
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text("Color", color = Color.White)
        }

        // Exit Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(x = offsetX)
        ) {
            Button(
                onClick = {
                    animateExit = true
                    coroutineScope.launch {
                        delay(1000)
                        activity.finish()
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Exit")
            }
        }
    }
}
