package com.luke.audexchange

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.compose.ui.res.stringResource

data class Stats(val latest: Double, val avg: Double, val min: Double, val max: Double) {
    fun changePercent(): Double = if (avg != 0.0) (latest - avg) / avg * 100 else 0.0
}

@Composable
fun displayData(rateList: List<RateData>) {
    val valuesUSD = rateList.map { it.AUDUSD }
    val valuesCNY = rateList.map { it.AUDCNY }

    fun List<Double>.averageOrZero() = if (isNotEmpty()) average() else 0.0

    val statsUSD = Stats(
        latest = valuesUSD.firstOrNull() ?: 0.0,
        avg = valuesUSD.averageOrZero(),
        min = valuesUSD.minOrNull() ?: 0.0,
        max = valuesUSD.maxOrNull() ?: 0.0
    )

    val statsCNY = Stats(
        latest = valuesCNY.firstOrNull() ?: 0.0,
        avg = valuesCNY.averageOrZero(),
        min = valuesCNY.minOrNull() ?: 0.0,
        max = valuesCNY.maxOrNull() ?: 0.0
    )

    Text("AUD/USD: Now：%.4f，Avg：%.4f，Min：%.4f，Max：%.4f，%.2f%% %s".format(
        statsUSD.latest, statsUSD.avg, statsUSD.min, statsUSD.max,
        statsUSD.changePercent(), if (statsUSD.changePercent() >= 0) "↑" else "↓"
    ), color = if (statsUSD.changePercent() >= 0) Color(0xFF388E3C) else Color.Red)

    Text("AUD/CNY: Now：%.4f，Avg：%.4f，Min：%.4f，Max：%.4f，%.2f%% %s".format(
        statsCNY.latest, statsCNY.avg, statsCNY.min, statsCNY.max,
        statsCNY.changePercent(), if (statsCNY.changePercent() >= 0) "↑" else "↓"
    ), color = if (statsCNY.changePercent() >= 0) Color(0xFF388E3C) else Color.Red)

    Spacer(modifier = Modifier.height(24.dp))
    Text(stringResource(R.string.past_30_days))
    Spacer(modifier = Modifier.height(8.dp))

    Column {
        Row {
            Text("Day", modifier = Modifier.weight(1f))
            Text("AUD/USD", modifier = Modifier.weight(1f))
            Text("Change", modifier = Modifier.weight(1f))
            Text("AUD/CNY", modifier = Modifier.weight(1f))
            Text("Change", modifier = Modifier.weight(1f))
        }

        rateList.forEach {
            val changeUSD = (it.AUDUSD - statsUSD.avg) / statsUSD.avg * 100
            val changeCNY = (it.AUDCNY - statsCNY.avg) / statsCNY.avg * 100

            Row {
                Text(it.date, modifier = Modifier.weight(1f))
                Text("%.4f".format(it.AUDUSD), modifier = Modifier.weight(1f))
                Text("%.2f%%".format(changeUSD), modifier = Modifier.weight(1f), color = if (changeUSD >= 0) Color(0xFF388E3C) else Color.Red)
                Text("%.4f".format(it.AUDCNY), modifier = Modifier.weight(1f))
                Text("%.2f%%".format(changeCNY), modifier = Modifier.weight(1f), color = if (changeCNY >= 0) Color(0xFF388E3C) else Color.Red)
            }
        }
    }
}

@Composable
fun ExchangeRateScreen() {
    var rateList by remember { mutableStateOf<List<RateData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            rateList = fetchFromFrankfurter()
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.title_exchange_panel),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            if (rateList.isNotEmpty()) {
                displayData(rateList)
            }
        }
    }
}
