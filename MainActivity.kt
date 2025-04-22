package com.example.yp_qr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.yp_qr.intenthandlers.* // 👈 importa todos los handlers de una vez
import com.example.yp_qr.ui.theme.YpqrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (intent?.action) {
            "icg.actions.electronicpayment.rfhnlyp.INITIALIZE" -> {
                InitializeHandler(this).handle()
                return
            }
            "icg.actions.electronicpayment.rfhnlyp.SHOW_SETUP_SCREEN" -> {
                ShowSetupHandler(this).handle()
                return
            }
            "icg.actions.electronicpayment.rfhnlyp.FINALIZE" -> {
                FinalizeHandler(this).handle()
                return
            }
            "icg.actions.electronicpayment.rfhnlyp.GET_BEHAVIOR" -> {
                GetBehaviorHandler(this).handle()
                return
            }
            "icg.actions.electronicpayment.rfhnlyp.GET_VERSION" -> {
                GetVersionHandler(this).handle()
                return
            }
            "icg.actions.electronicpayment.rfhnlyp.GET_CUSTOM_PARAMS" -> {
                GetCustomParamsHandler(this).handle()
                return
            }
            "icg.actions.electronicpayment.rfhnlyp.TRANSACTION" -> {
                TransactionHandler(this).handle()
                return
            }
        }

        // Si no es un Intent del sistema HioPosCloud, carga normalmente la UI:
        enableEdgeToEdge()
        setContent {
            YpqrTheme {
                Surface(modifier = Modifier, color = Color(0xFF2196F3)) {
                    AppNavigation()
                }
            }
        }
    }
}
