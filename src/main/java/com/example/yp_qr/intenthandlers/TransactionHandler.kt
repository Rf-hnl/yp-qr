package com.example.yp_qr.intenthandlers

import android.app.Activity
import android.content.Intent
import android.util.Log

class TransactionHandler(private val activity: Activity) {

    private val TAG = "TransactionHandler"

    fun handle() {
        Log.i(TAG, "🟢 Iniciando TransactionHandler")

        val intent = activity.intent
        val extras = intent.extras

        // 🔍 Imprimir todos los extras
        Log.d(TAG, "--- Volcando Extras del Intent ---")
        extras?.keySet()?.forEach { key ->
            val value = extras.get(key)
            Log.d(TAG, "🧩 $key -> $value (${value?.javaClass?.simpleName ?: "null"})")
        } ?: Log.d(TAG, "⚠️ El Intent no contiene extras.")
        Log.d(TAG, "--- Fin del volcado de Extras ---")

        // 🧾 Leer y validar datos
        val transactionType = intent.getStringExtra("TransactionType") ?: "SALE"
        val amountRaw = intent.getStringExtra("Amount") ?: "000"
        val tipAmountRaw = intent.getStringExtra("TipAmount") ?: "000"
        val taxAmountRaw = intent.getStringExtra("TaxAmount") ?: "000"

        val transactionIdInt = intent.getIntExtra("TransactionId", -1)
        val transactionId = if (transactionIdInt != -1) {
            transactionIdInt.toString()
        } else {
            Log.w(TAG, "⚠️ TransactionId no recibido. Generando ID temporal.")
            "TX-${System.currentTimeMillis()}"
        }

        // 💵 Conversión de montos
        val amount = convertirMonto(amountRaw)
        val tipAmount = convertirMonto(tipAmountRaw)
        val taxAmount = convertirMonto(taxAmountRaw)

        // ✅ Log resumen
        Log.i(TAG, """
            ✅ Detalles de la transacción:
            • Tipo: $transactionType
            • ID: $transactionId
            • Monto: $amount
            • Propina: $tipAmount
            • IVA: $taxAmount
        """.trimIndent())

        // 🧾 Crear recibo
        val merchantReceipt = """
            <Receipt numCols="42">
              <ReceiptLine type="TEXT">
                <Formats><Format from="0" to="42">BOLD</Format></Formats>
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

        Log.d(TAG, "--- merchantReceipt ---\n$merchantReceipt\n--- fin ---")

        // 📤 Enviar resultado al sistema HioPos
        val result = Intent("icg.actions.electronicpayment.tefbanesco.TRANSACTION").apply {
            putExtra("TransactionResult", "ACCEPTED")
            putExtra("TransactionType", transactionType)
            putExtra("Amount", amountRaw)
            putExtra("TipAmount", tipAmountRaw)
            putExtra("TaxAmount", taxAmountRaw)
            putExtra("TransactionData", "DATA-$transactionId")
            putExtra("MerchantReceipt", merchantReceipt)
        }

        activity.setResult(Activity.RESULT_OK, result)
        activity.finish()
        Log.i(TAG, "✅ Resultado enviado. Activity finalizada.")
    }

    // 🔧 Conversor de montos
    private fun convertirMonto(montoStr: String?): String {
        return try {
            if (montoStr.isNullOrBlank() || montoStr.length < 3) return "0.00"
            val formatted = montoStr.dropLast(2) + "." + montoStr.takeLast(2)
            "%.2f".format(formatted.toDouble())
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error al convertir monto: $montoStr", e)
            "0.00"
        }
    }
}
