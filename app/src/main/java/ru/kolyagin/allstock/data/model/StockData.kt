package ru.kolyagin.allstock.data.model

import com.google.gson.annotations.SerializedName

data class StockData(
    @SerializedName("data")
    val `data`: List<Stock>,
    @SerializedName("type")
    val type: String
)