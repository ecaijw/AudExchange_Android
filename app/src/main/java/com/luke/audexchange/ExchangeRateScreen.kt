package com.luke.audexchange

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*

@Composable
fun ExchangeRateScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "澳元汇率面板",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text("AUD/USD: 0.6472  ↑ 0.47%", color = Color(0xFF388E3C))
        Text("AUD/CNY: 4.6761  ↑ 0.78%", color = Color(0xFF388E3C))

        Spacer(modifier = Modifier.height(16.dp))
        Text("【此处可添加折线图 - 需要依赖 chart 库】", modifier = Modifier.padding(8.dp))

        Spacer(modifier = Modifier.height(24.dp))
        Text("过去30天汇率表格")

        Spacer(modifier = Modifier.height(8.dp))
        Column {
            listOf(
                "2025-06-05 | AUD/USD: 0.6509 (+1.04%) | AUD/CNY: 4.6728 (+0.70%)",
                "2025-06-04 | AUD/USD: 0.6486 (+0.68%) | AUD/CNY: 4.6624 (+0.48%)",
                "2025-06-03 | AUD/USD: 0.6455 (+0.20%) | AUD/CNY: 4.6395 (-0.01%)"
            ).forEach {
                Text(text = it, modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}
