package com.example.yp_qr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.yp_qr.ui.theme.YpqrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YpqrTheme {
                Surface(
                    modifier = Modifier,
                    color = Color(0xFF2196F3) // Fondo azul
                ) {
                    AppNavigation()  // Se usa la navegación completa
                }
            }
        }
    }
}
