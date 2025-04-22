package com.example.yp_qr.intenthandlers

import android.app.Activity
import android.content.Intent
import android.util.Log

class TransactionHandler(private val activity: Activity) {

    fun handle() {
        val transactionType = activity.intent.getStringExtra("TransactionType") ?: "SALE"
        val transactionId = activity.intent.getStringExtra("TransactionId") ?: "TX-${System.currentTimeMillis()}"
        val amount = activity.intent.getStringExtra("Amount") ?: "000"
        val tipAmount = activity.intent.getStringExtra("TipAmount") ?: "000"
        val taxAmount = activity.intent.getStringExtra("TaxAmount") ?: "000"

        Log.d("TransactionHandler", "➡️ Transacción recibida: $transactionType, monto $amount")

        // Aquí podrías hacer lógica real (llamada API, validaciones, etc.)
        // Nosotros devolvemos una respuesta simulada con XML básico

        val merchantReceipt = """
            <Receipt numCols="42">
              <ReceiptLine type="TEXT">
                <Formats>
                  <Format from="0" to="42">BOLD</Format>
                </Formats>
                <Text>     Transacción Exitosa     </Text>
              </ReceiptLine>
              <ReceiptLine type="TEXT">
                <Formats><Format from="0" to="42">NORMAL</Format></Formats>
                <Text>ID: $transactionId</Text>
              </ReceiptLine>
              <ReceiptLine type="TEXT">
                <Formats><Format from="0" to="42">NORMAL</Format></Formats>
                <Text>Monto: $amount</Text>
              </ReceiptLine>
            </Receipt>
        """.trimIndent()

        val result = Intent("icg.actions.electronicpayment.rfhnlyp.TRANSACTION").apply {
            putExtra("TransactionResult", "ACCEPTED")
            putExtra("TransactionType", transactionType)
            putExtra("Amount", amount)
            putExtra("TipAmount", tipAmount)
            putExtra("TaxAmount", taxAmount)
            putExtra("TransactionData", "DATA-$transactionId") // Para referencia futura
            putExtra("MerchantReceipt", merchantReceipt)
        }

        activity.setResult(Activity.RESULT_OK, result)
        activity.finish()
    }
}
