package com.luke.audexchange

import kotlinx.serialization.Serializable

@Serializable
data class RateData(
    val date: String,
    val AUDUSD: Double,
    val AUDCNY: Double
)
